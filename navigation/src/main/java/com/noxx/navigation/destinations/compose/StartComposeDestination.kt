package com.noxx.navigation.destinations.compose

import com.noxx.navigator.model.Destination
import com.noxx.navigator.navRoute.navRoute

object StartComposeDestination : Destination() {

    override val navRoot: String = "app/startCompose"

    fun createRoute() = navRoute {
        root(navRoot)
    }
}
