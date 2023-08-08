package com.noxx.navigator.generator.parser

import com.noxx.navigator.generator.model.ArgumentItem
import com.noxx.navigator.generator.model.FragmentItem
import com.noxx.navigator.generator.model.Navigation
import org.jsoup.Jsoup

internal object NavigationGraphParser {

    fun parse(graph: String): Navigation {
        val document = Jsoup.parse(graph)
        val fragments = document.select("navigation")
            .select("fragment")
            .map {
                FragmentItem(
                    id = it.attr("android:id"),
                    name = it.attr("android:name"),
                    arguments = it.select("argument").map {
                        val xmlDefValue = it.attrOrNull("android:defaultValue")
                        val nullable = it.attrOrNull("android:nullable")

                        ArgumentItem(
                            name = it.attr("android:name"),
                            defaultValue = it.attrOrNull("android:defaultValue"),
                            nullable = when {
                                nullable == null -> {
                                    false
                                }

                                nullable == "true" && xmlDefValue == null -> {
                                    true
                                }

                                nullable == "true" -> {
                                    true
                                }
                                else -> false
                            },
                            argType = it.attr("app:argType")
                        )
                    }
                )
            }
        return Navigation(fragments)
    }
}
