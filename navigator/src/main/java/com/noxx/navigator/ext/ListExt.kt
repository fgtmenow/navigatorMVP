package com.noxx.navigator.ext

fun <T> List<T>.secondToLast(): T? {
    if (size < 2) {
        return null
    }
    return this[size - 2]
}
