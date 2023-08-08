package com.noxx.navigator.navRoute

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.noxx.navigator.ext.ARG_ARGUMENTS
import com.noxx.navigator.ext.ARG_PARCEL
import com.noxx.navigator.ext.getStringValue
import com.noxx.navigator.ext.toBase64

/**
 * navRoute {
 *  root(
 * }
 */
fun navRoute(init: NavRoute.() -> Unit): String {
    val navRoute = NavRoute()
    navRoute.init()
    val builder = Uri.Builder().path(navRoute.root)
    val hasParcelable = navRoute.args.keySet().any { navRoute.args.get(it) is Parcelable }
    if (hasParcelable) {
        builder
            .appendQueryParameter(ARG_PARCEL, "${true}")
            .appendQueryParameter(ARG_ARGUMENTS, navRoute.args.toBase64())
    } else {
        navRoute.args.keySet().onEach {
            builder.appendQueryParameter(it, navRoute.args.getStringValue(it))
        }
    }

    return builder.build().toString()
}

data class NavRoute(
    var root: String = "",
    val args: Bundle = Bundle()
) {

    fun root(root: String) {
        this.root = root
    }

    @JvmName("paramList")
    fun param(entries: List<Pair<String, Any>>) {
        args.putAll(bundleOf(*entries.toTypedArray()))
    }

    @JvmName("param")
    fun param(entries: Pair<String, Any>) {
        param(listOf(entries))
    }
}
