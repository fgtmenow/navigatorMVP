package com.noxx.navigator.model

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * List of feature module destinations
 *
 * Must be provided by hilt
 */
data class FeatureDestinations(
    val destinations: List<Pair<Destination, @Composable (NavHostController, NavBackStackEntry) -> Unit>>
)
