package com.noxx.navigator.navigator

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.noxx.navigator.model.NavEvent
import kotlinx.coroutines.channels.Channel

interface Navigator {

    var navController: NavHostController?

    val destinations: Channel<NavEvent>

    /**
     * Navigate up
     */
    fun navigateUp(popUpDestination: String? = null)

    /**
     * Navigate to screen by destination
     * @param route destination route
     * @param inlusive clear backstack
     * @param popUpDestination pop to destination
     * @param parcelableArgs list of parcelable arguments
     */
    fun navigate(
        route: String,
        popUpDestination: String? = null,
        inlusive: Boolean = false
    )

    /**
     * Set result to previous backStack Entry
     */
    fun <T> setNavigationResult(key: String, value: T?) {
        if (navController?.previousBackStackEntry?.lifecycle?.currentState != Lifecycle.State.DESTROYED) {
            navController?.previousBackStackEntry?.savedStateHandle?.set(key, value)
        }
    }

    /**
     * Get result from current backStack Entry
     */
    fun <T> getNavigationResult(key: String): T? {
        return navController?.currentBackStackEntry?.savedStateHandle?.get<T>(key)
    }

    /**
     * Close application
     */
    fun closeApp()
}
