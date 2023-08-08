package com.noxx.navigation.destinations.fragment

import com.noxx.navigator.model.Destination
import com.noxx.navigator.navRoute.navRoute

object ResultFragmentDestination : Destination() {

    override val navRoot: String = "app/fragmentResult"

    fun createRoute() = navRoute {
        root(navRoot)
    }

    const val REQUEST_FRAGMENT_KEY = "fragmentResult"
}
