package com.noxx.navmvp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.noxx.designsystem.theme.NavMVPTheme
import com.noxx.navigation.destinations.compose.StartComposeDestination
import com.noxx.navigation.destinations.fragment.Fragment2Destination
import com.noxx.navigator.composable.NavContainer
import com.noxx.navigator.ext.LocalNavigator
import com.noxx.navigator.model.AppDestinations
import com.noxx.navigator.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appDestinations: AppDestinations

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)
            CompositionLocalProvider(
                LocalNavigator provides navigator
            ) {
                NavMVPTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val backPressState = remember { mutableStateOf<BackPress>(BackPress.Idle) }
                        val showToast = remember { mutableStateOf(false) }
                        val context = LocalContext.current

                        if (showToast.value) {
                            Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT)
                                .show()
                            showToast.value = false
                        }

                        ModalBottomSheetLayout(bottomSheetNavigator) {
                            NavContainer(
                                navController = navController,
                                navigator = navigator,
                                startDestination = StartComposeDestination.createRoute(),
                                route = "app",
                                onBackCallback = { prevBackStackEntry ->
                                    if (prevBackStackEntry == null) {
                                        val res = backPressState.value != BackPress.Idle
                                        backPressState.value = BackPress.InitialTouch
                                        showToast.value = true
                                        res
                                    } else {
                                        true
                                    }
                                }
                            ) {
                                bottomSheet(route = "app/sheet") {
                                    Column {
                                        Text(
                                            text = "bottom sheet title"
                                        )
                                        Text(
                                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque quis faucibus sem. Pellentesque enim tortor, commodo vel commodo vel, dictum id libero. Fusce fermentum lacus quis lacus fermentum rutrum. Donec a velit vitae justo vulputate luctus. Quisque sapien ex, ullamcorper ut erat vel, tempus vestibulum massa. Praesent mollis vitae lacus sit amet dignissim. Vestibulum a ante urna.\n" +
                                                "\n" +
                                                "Vestibulum nulla enim, ullamcorper vel porta in, sodales eu dolor. Sed faucibus libero sit amet malesuada iaculis. Aliquam erat volutpat. Ut ac ullamcorper velit. Mauris varius lacinia eros a consectetur. Nunc congue, augue id interdum blandit, risus enim scelerisque mauris, eu vulputate nunc enim vel elit. Cras ut eleifend nibh, sit amet maximus ex. Nulla sagittis augue non ex bibendum, eu accumsan arcu rutrum. Cras quis risus non mi placerat vehicula a eget libero. Praesent faucibus dapibus hendrerit. Proin congue nunc eu ex dictum, ac rhoncus metus auctor. Sed tempus lectus at laoreet blandit."
                                        )
                                        Button(onClick = { navigator.navigateUp() }) {
                                            Text(
                                                text = "close"
                                            )
                                        }
                                        Button(onClick = { navigator.navigate(Fragment2Destination.createRoute()) }) {
                                            Text(
                                                text = "navigate fo fragment"
                                            )
                                        }
                                    }
                                }
                                appDestinations.destinations.forEach { entry ->
                                    entry.first.attach(this) {
                                        entry.second(navController, it)
                                    }
                                }
                            }
                        }
                        LaunchedEffect(key1 = backPressState.value) {
                            if (backPressState.value == BackPress.InitialTouch) {
                                delay(1000)
                                backPressState.value = BackPress.Idle
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}
