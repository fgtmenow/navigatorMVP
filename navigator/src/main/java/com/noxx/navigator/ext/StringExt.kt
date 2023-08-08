package com.noxx.navigator.ext

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import com.noxx.navigator.model.Destination

private const val DELIMITER = "?"
private const val AMPERSAND = "&"
const val ARG_PARCEL = "parcel"
const val ARG_ARGUMENTS = "arg"

/**
 * Build compose navigation route from root field and named arguments list
 */
fun buildFullRoute(root: String, arguments: List<NamedNavArgument>): String {
    val hasParcelArgument = arguments.any { it.argument.type is NavType.ParcelableType }
    return when {
        hasParcelArgument -> {
            "$root$DELIMITER$ARG_PARCEL={$ARG_PARCEL}$AMPERSAND$ARG_ARGUMENTS={$ARG_ARGUMENTS}"
        }

        arguments.isEmpty() -> {
            root
        }

        else -> {
            root + DELIMITER + arguments.joinToString(AMPERSAND) { "${it.name}={${it.name}}" }
        }
    }
}

fun NavBackStackEntry.parseParcelableCompatArgs(): Bundle? {
    val args = this.arguments ?: Bundle.EMPTY
    return if (args.containsKey(ARG_PARCEL)) {
        val bundle = args.getString(ARG_ARGUMENTS)?.fromBase64()
        bundle?.classLoader = Destination::class.java.classLoader
        bundle
    } else {
        this.arguments
    }
}
