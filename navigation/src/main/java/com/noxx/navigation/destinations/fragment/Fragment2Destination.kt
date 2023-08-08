package com.noxx.navigation.destinations.fragment

import com.noxx.navigator.model.Destination
import com.noxx.navigator.navRoute.navRoute

object Fragment2Destination : Destination() {

    override val navRoot: String = "app/fragment2"

    fun createRoute() = navRoute {
        root(navRoot)
    }
}
