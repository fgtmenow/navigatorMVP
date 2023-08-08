package com.noxx.navigator.composable

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.net.toUri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.noxx.navigator.model.NavEvent
import com.noxx.navigator.navigator.Navigator
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@Composable
fun NavContainer(
    navController: NavHostController,
    navigator: Navigator,
    startDestination: String,
    route: String,
    onBackCallback: (prevBackStackEntry: NavBackStackEntry?) -> Boolean = { true },
    graphBuilder: NavGraphBuilder.() -> Unit
) {
    LaunchedEffect(navController) {
        navigator.navController = navController
    }
    BackHandler {
        navigator.navigateUp()
    }
    LaunchedEffect(navController) {
        navigator.destinations.receiveAsFlow().collect { event ->
            when (event) {
                is NavEvent.NavigateUp -> {
                    val popUpRoute = event.popUpDestination
                        ?: navController.previousBackStackEntry?.destination?.route
                    if (navController.previousBackStackEntry == null) {
                        if (onBackCallback(navController.previousBackStackEntry)) {
                            closeApp(navController)
                        }
                    } else {
                        navigateUp(
                            navController,
                            popUpRoute,
                            event.popUpInclusive
                        )
                    }
                }

                is NavEvent.Directions -> {
                    try {
                        val graph = navController.graph
                        val graphName = graph.route ?: ""
                        val destination = event.destination
                        if (!destination.startsWith(graphName)) {
                            return@collect
                        }
                        navigate(
                            event,
                            navController
                        )
                    } catch (e: Throwable) {
                        Timber.e(e)
                    }
                }

                is NavEvent.CloseApp -> {
                    closeApp(navController)
                }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = route
    ) {
        graphBuilder(this)
    }
}

private fun navigateUp(
    navController: NavHostController,
    popUpDestination: String?,
    popUpInclusive: Boolean = false
) {
    Timber.d("Navigate up to $popUpDestination, popUpInclusive=$popUpInclusive")
    if (popUpDestination != null) {
        if (navController.previousBackStackEntry?.destination?.route == popUpDestination) {
            navController.navigateUp()
        } else {
            navController.popBackStack(popUpDestination, popUpInclusive)
        }
    } else {
        navController.navigateUp()
    }
}

private fun closeApp(
    navController: NavHostController
) {
    (navController.context as Activity).finish()
}

private fun navigate(
    event: NavEvent.Directions,
    navController: NavHostController
) {
    val request = NavDeepLinkRequest.Builder.fromUri(
        NavDestination.createRoute(event.destination).toUri()
    ).build()

    val deepLinkMatch = navController.graph.matchDeepLink(request)

    when {
        deepLinkMatch != null -> {
            navigate(navController, event)
        }

        else -> {
            Timber.d("Skip route ${event.destination}")
        }
    }
}

private fun navigate(
    navController: NavHostController,
    event: NavEvent.Directions
) {
    Timber.d("Navigate to route ${event.destination}, clear=${event.inlusive}, popUpDestination=${event.popUpDestination}")
    navController.navigate(event.destination) {
        val popUpDestination = event.popUpDestination
        if (popUpDestination != null) {
            popUpTo(popUpDestination) {
                inclusive = event.inlusive
            }
        }
    }
}
