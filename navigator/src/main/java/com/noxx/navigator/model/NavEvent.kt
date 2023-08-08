package com.noxx.navigator.model

/**
 * Navigation events
 */
sealed class NavEvent {

    /**
     * Navigate Up
     */
    data class NavigateUp(
        val popUpDestination: String? = null,
        val popUpInclusive: Boolean = false
    ) : NavEvent()

    /**
     * Close application
     */
    data object CloseApp : NavEvent()

    /**
     * Navigate to given destination
     */
    class Directions(
        val destination: String,
        val popUpDestination: String? = null,
        val inlusive: Boolean = false
    ) : NavEvent() {

        override fun toString(): String {
            return "Directions(destination='$destination', popUpDestination=$popUpDestination, inlusive=$inlusive)"
        }
    }
}
