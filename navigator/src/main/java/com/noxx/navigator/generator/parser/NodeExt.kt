package com.noxx.navigator.generator.parser

import org.jsoup.nodes.Node

fun Node.attrOrNull(name: String): String? {
    if (!this.attributes().hasKey(name.lowercase())) {
        return null
    }
    return this.attr(name)
}
