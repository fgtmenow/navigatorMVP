package com.noxx.navigator.ext

import androidx.compose.runtime.staticCompositionLocalOf
import com.noxx.navigator.navigator.Navigator

/**
 * [com.noxx.navigator.navigator.Navigator] instance for composable usage
 */
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("LocalBetNavigator not provided")
}
