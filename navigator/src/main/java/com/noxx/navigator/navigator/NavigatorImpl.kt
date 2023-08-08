package com.noxx.navigator.navigator

import androidx.navigation.NavHostController
import com.noxx.navigator.model.NavEvent
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NavigatorImpl @Inject constructor() : Navigator {

    override val destinations = Channel<NavEvent>()

    override var navController: NavHostController? = null

    override fun navigateUp(popUpDestination: String?) {
        destinations.trySend(NavEvent.NavigateUp(popUpDestination))
    }

    override fun navigate(
        route: String,
        popUpDestination: String?,
        inlusive: Boolean
    ) {
        destinations.trySend(
            NavEvent.Directions(
                destination = route,
                inlusive = inlusive,
                popUpDestination = popUpDestination
            )
        )
    }

    override fun closeApp() {
        destinations.trySend(NavEvent.CloseApp)
    }
}
