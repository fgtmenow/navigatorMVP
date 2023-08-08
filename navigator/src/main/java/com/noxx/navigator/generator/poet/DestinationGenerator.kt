package com.noxx.navigator.generator.poet

import androidx.navigation.NamedNavArgument
import com.noxx.navigator.generator.model.FragmentItem
import com.noxx.navigator.model.Destination
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import java.io.File

object DestinationGenerator {

    fun generate(
        fragmentItem: FragmentItem,
        srcPath: File,
        moduleName: String,
        packageName: String
    ): String {
        val className = fragmentItem.getClassName() + "Destination"
        val navPath = fragmentItem.getNavId()

        // create file
        val fileBuilder = FileSpec.builder(packageName, className)
            .addImport("com.noxx.navigator.navRoute", "navRoute")
            .addImport("androidx.navigation", "navArgument")
            .addImport("androidx.navigation", "NavType")

        // create object class
        val obj = TypeSpec
            .objectBuilder(className)
            .superclass(Destination::class)

        // navroot fun
        obj.addProperty(
            PropertySpec
                .builder("navRoot", String::class, KModifier.OVERRIDE)
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement("return \"app/$navPath\"").build()
                )
                .build()
        )

        var createRouteBody = FunSpec
            .builder("createRoute")
            .returns(String::class)
            .addCode("return ")
            .beginControlFlow("navRoute")
            .addCode("root(navRoot)")
            .addCode("\n")

        // add arguments, createRoute, const vals
        if (fragmentItem.arguments.isNotEmpty()) {
            val argListGetter = FunSpec.getterBuilder()
                .addCode("return listOf(\n")
            fragmentItem.arguments.onEach {
                argListGetter.beginControlFlow("navArgument(${it.getConstName()})")
                    .addStatement("type = ${it.getNavType()}")
                    .apply {
                        when {
                            it.nullable && (it.argType.contains("StringType") || it.argType.contains(
                                "Parcelable"
                            )) -> {
                                this.addStatement("defaultValue = ${it.defaultValue}")
                                this.addStatement("nullable = ${it.getNullableValue()}")
                            }
                            else -> {

                            }
                        }
                    }
                    .endControlFlow()
                    .addCode(",")
            }
            argListGetter.addCode(")")
            val args = PropertySpec
                .builder(
                    "arguments",
                    List::class.asClassName()
                        .parameterizedBy(NamedNavArgument::class.asTypeName())
                )
                .getter(argListGetter.build())
                .addModifiers(KModifier.OVERRIDE)

            fragmentItem.arguments.onEach {
                obj.addProperty(
                    PropertySpec
                        .builder(
                            it.getConstName(),
                            String::class
                        )
                        .addModifiers(KModifier.CONST, KModifier.PRIVATE)
                        .initializer(CodeBlock.of("\"${it.name}\""))
                        .build()
                )
            }



            fragmentItem.arguments
                .sortedBy { it.defaultValue != null }
                .onEach {
                    createRouteBody
                        .addParameter(
                            ParameterSpec
                                .builder(
                                    it.name,
                                    it.getParamType().asTypeName()
                                )
                                .apply {
                                    if (it.defaultValue != null) {
                                        this.defaultValue(CodeBlock.of(it.defaultValue, ""))
                                    }
                                }
                                .build()

                        )
                        .addCode("param(")
                        .addCode(it.getConstName()).addCode(" to ").addCode(it.name)
                        .addCode(")")
                        .addCode("\n")
                }

            obj.addProperty(args.build())
        }

        createRouteBody = createRouteBody.endControlFlow()
        obj.addFunction(createRouteBody.build())
        fileBuilder.addType(obj.build())

        val subDir = File(srcPath, moduleName)
        subDir.mkdirs()
        val resultFile = File(subDir, "$className.kt")
        resultFile.writeText(fileBuilder.build().toString())
        return "$packageName.$className"
    }
}
