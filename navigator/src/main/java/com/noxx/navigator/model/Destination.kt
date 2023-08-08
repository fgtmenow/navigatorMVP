package com.noxx.navigator.model

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.noxx.navigator.ext.ARG_ARGUMENTS
import com.noxx.navigator.ext.ARG_PARCEL
import com.noxx.navigator.ext.buildFullRoute
import com.noxx.navigator.ext.toBase64

/**
 * Navigation destination
 */
abstract class Destination {

    /**
     * Navigation route root like /app/screen, use for popUpDestination
     */
    abstract val navRoot: String

    /**
     * Deeplink list, using to create navigation graph
     */
    protected open val deepLinks: List<NavDeepLink>
        get() = emptyList()

    /**
     * Argument list, using to create navigation graph and navigation route
     */
    protected open val arguments: List<NamedNavArgument>
        get() = emptyList()

    /**
     * Navigation route like /app/screen?arg1={arg1}
     */
    private fun graphRoute(): String = buildFullRoute(navRoot, arguments)

    /**
     * Provide list of navigation arguments
     */
    private fun graphArguments(): List<NamedNavArgument> {
        val hasParcelArgument = arguments.any { it.argument.type is NavType.ParcelableType }
        return if (hasParcelArgument) {
            listOf(
                navArgument(ARG_PARCEL) {
                    type = NavType.BoolType
                    defaultValue = true
                },
                navArgument(ARG_ARGUMENTS) {
                    type = NavType.StringType
                    defaultValue = Bundle.EMPTY.toBase64()
                }
            )
        } else {
            arguments
        }
    }

    /**
     * Attach destination to navGraph
     */
    fun attach(navGraphBuilder: NavGraphBuilder, content: @Composable (NavBackStackEntry) -> Unit) {
        navGraphBuilder.composable(graphRoute(), graphArguments(), deepLinks) {
            content(it)
        }
    }
}
