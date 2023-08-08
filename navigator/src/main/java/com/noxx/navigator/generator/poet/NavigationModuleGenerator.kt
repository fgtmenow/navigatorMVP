package com.noxx.navigator.generator.poet

import android.content.Context
import com.noxx.navigator.generator.model.FragmentItem
import com.noxx.navigator.model.FeatureDestinations
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.multibindings.IntoSet
import java.io.File

object NavigationModuleGenerator {

    fun generate(
        fragments: List<FragmentItem>,
        packageName: String,
        srcDir: File,
        destinationImports: List<String>
    ): String {
        val className = "NavigationModule"
        val fileBuilder = FileSpec.builder(packageName, className)
            .addImport("android.content", "Context")
            .addImport("androidx.fragment.app", "FragmentActivity")
            .addImport("dagger.hilt", "InstallIn")
            .addImport("dagger.hilt.android.components", "ActivityComponent")
            .addImport("com.noxx.navigator.composable", "FragmentContainer")

        destinationImports.onEach {
            fileBuilder.addImport(it, "")
        }

        val obj = TypeSpec.objectBuilder(className)
        obj.addAnnotation(Module::class)
        obj.addAnnotation(
            AnnotationSpec.builder(InstallIn::class).addMember("ActivityComponent::class").build()
        )

        val provideFun = FunSpec.builder("provideDestinations")
            .returns(FeatureDestinations::class)
            .addAnnotation(ActivityScoped::class)
            .addAnnotation(Provides::class)
            .addAnnotation(IntoSet::class)
            .addParameter(
                ParameterSpec.builder("context", Context::class.asTypeName())
                    .addAnnotation(AnnotationSpec.builder(ActivityContext::class).build())
                    .build()
            )
            .addStatement("val fragmentManager = (context as FragmentActivity).supportFragmentManager")
            .addCode("return FeatureDestinations(listOf(\n")

        fragments.onEach { fragmentItem ->
            fileBuilder.addImport(fragmentItem.name, "")
            provideFun.addCode(fragmentItem.getClassName() + "Destination")
            provideFun.addCode(" to { navHostController, navBackStackEntry ->")
            provideFun.addCode("   FragmentContainer<${fragmentItem.getClassName()}>(\n")
            provideFun.addCode("   fragmentManager,\n")
            provideFun.addCode("   navHostController,\n")
            provideFun.addCode("   navBackStackEntry\n")
            provideFun.addCode(" )\n")
            provideFun.addCode(" },\n")
        }

        provideFun.addCode("))")

        obj.addFunction(provideFun.build())

        // add object to file
        fileBuilder.addType(obj.build())
        // print file
        //  fileBuilder.build().writeTo(System.err)

        srcDir.mkdirs()
        val resultFile = File(srcDir, "NavigationModule.kt")
        resultFile.writeText(fileBuilder.build().toString())
        return packageName + "." + className
    }
}
