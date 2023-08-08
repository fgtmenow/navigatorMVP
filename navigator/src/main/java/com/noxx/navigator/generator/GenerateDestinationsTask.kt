package com.noxx.navigator.generator

import com.noxx.navigator.generator.parser.NavigationGraphParser
import com.noxx.navigator.generator.poet.DestinationGenerator
import com.noxx.navigator.generator.poet.NavigationModuleGenerator
import com.noxx.navigator.generator.utils.AndroidProjectUtils
import java.io.File

fun main(args: Array<String>) {
    val rootDir = File(args[0])
    val destinationModuleSrcPath = File(args[1])
    val destinationModulePackageName = args[2]
    println(" ")
    println("Run generator with params: rootDir = $rootDir, separator=${File.separator}")
    println("destinationModuleSrcPath = $destinationModuleSrcPath")
    println("destinationModulePackageName = $destinationModulePackageName")
    val navigatableModuleDirs = AndroidProjectUtils.getNavigationGraphsFiles(rootDir)
        .map {
            val parts = it.absolutePath.split(File.separator)
            val index = parts.indexOfFirst { it == "src" }
            parts.take(index).joinToString(File.separator)
        }.map { File(it) }
    println(" ")
    println("Find modules: ")
    navigatableModuleDirs.onEachIndexed { index, path ->
        println("${index + 1}. $path")
    }
    println(" ")

    navigatableModuleDirs.onEach { path ->
        val moduleName = path.absolutePath.split(File.separator).last()
        println("Process Module: $moduleName")
        val packageName = AndroidProjectUtils.getPackageName(path)
        if (packageName != null) {
            println("Module package name: $packageName")
            val srcPath = File(path, "src/main/java/${packageName?.replace(".", "/")}")
            val diDir = File(srcPath, "di")
            println("DI dir: $diDir")
            val navFiles = AndroidProjectUtils.getNavigationGraphsFiles(path)
            println("Nav files: ${navFiles.joinToString()}")

            val fragments = navFiles
                .map {
                    NavigationGraphParser.parse(it.readText(Charsets.UTF_8))
                }
                .flatMap { it.fragments }

            if (fragments.isNotEmpty()) {
                val directionImports = mutableListOf<String>()
                fragments.onEach {
                    val fullClassName = DestinationGenerator.generate(
                        fragmentItem = it,
                        srcPath = destinationModuleSrcPath,
                        moduleName = moduleName,
                        packageName = "$destinationModulePackageName.$moduleName"
                    )
                    println("Generated destination: $fullClassName")
                    directionImports.add(fullClassName)
                }

                val navModule = NavigationModuleGenerator.generate(
                    fragments = fragments,
                    packageName = "$packageName.di",
                    srcDir = diDir,
                    directionImports
                )
                println("Generated hilt nav module: $navModule")
            } else {
                println("No fragments. Skip module")
            }
        } else {
            println("Cant extract package name. Skip module")
        }
        println(" ")
    }
}
