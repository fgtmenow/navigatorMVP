package com.noxx.navigator.model

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * Application destinations,
 * used by collecting [com.noxx.navigator.model.FeatureDestinations] from each module and
 * provide all destionations for create navigation graph by [com.noxx.navigator.ext.addDestinations]
 */
data class AppDestinations(
    val destinations: List<Pair<Destination, @Composable (NavHostController, NavBackStackEntry) -> Unit>>
)
