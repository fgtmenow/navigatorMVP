package com.noxx.navigation.destinations.compose

import com.noxx.navigator.model.Destination
import com.noxx.navigator.navRoute.navRoute

object ResultComposeDestination : Destination() {

    override val navRoot: String = "app/composeResult"

    fun createRoute() = navRoute {
        root(navRoot)
    }

    const val REQUEST_COMPOSE_KEY = "composeResult"
}
