package com.noxx.navigator.generator.utils

import java.io.File

internal object AndroidProjectUtils {

    fun getNavigationGraphsFiles(modulePath: File): List<File> {
        return modulePath
            .walk()
            .toList()
            .filter { it.path.contains("build").not() }
            .filter { it.extension == "xml" }
            .filter {
                it.readText().contains("<navigation")
            }
    }

    fun getPackageName(modulePath: File): String? {
        return modulePath
            .walk()
            .toList()
            .filter { it.name == "build.gradle" }
            .map { it ->
                extractNamespace(it.readLines())
            }
            .firstOrNull()
    }

    fun extractNamespace(buildGradle: List<String>): String {
        return buildGradle
            .filter { it.contains("namespace") }
            .map { it.replace('\'','\"') }
            .map {
                it.substringAfter("\"").substringBefore('\"')
            }
            .first()
    }
}
