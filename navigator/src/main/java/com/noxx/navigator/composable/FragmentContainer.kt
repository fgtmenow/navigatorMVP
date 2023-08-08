package com.noxx.navigator.composable

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.noxx.navigator.ext.parseParcelableCompatArgs
import timber.log.Timber

/**
 * Composable that can store fragment instance with saving state and arguments
 * @param fragmentManager fragment manager instance from current activity
 * @param navHostController using to control fragment lifecycle
 * @param navBackStackEntry using to provide Bundle arguments
 */
@Composable
inline fun <reified T : Fragment> FragmentContainer(
    fragmentManager: FragmentManager,
    navHostController: NavHostController,
    navBackStackEntry: NavBackStackEntry
) {
    Timber.d("stack: ${navHostController.currentBackStack.value.map { it.id }}")
    val isCreation = rememberSaveable { mutableStateOf(true) }
    val containerId = rememberSaveable { mutableStateOf(View.generateViewId()) }
    val lifecycleEvent = rememberLifecycleEvent()

    val context = LocalContext.current
    val fragmentContainerView = remember {
        fragmentManager.findFragmentById(containerId.value)?.view
            ?.also { (it.parent as? ViewGroup)?.removeView(it) }
            ?: FragmentContainerView(context)
                .apply { id = containerId.value }
                .also {
                    fragmentManager.beginTransaction()
                        .replace(
                            containerId.value,
                            T::class.java.newInstance()
                                .apply {
                                    arguments = navBackStackEntry.parseParcelableCompatArgs()
                                }
                        )
                        .commit()
                }
    }

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_START) {
            val fragment = fragmentManager.findFragmentById(containerId.value)
            fragment?.apply {
                if (isCreation.value) {
                    isCreation.value = false
                } else {
                    onStart()
                    onResume()
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (navHostController.containsBackStackEntry(navBackStackEntry).not()) {
                fragmentManager.findFragmentById(containerId.value)?.let {
                    fragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
                }
            } else {
                val fragment = fragmentManager.findFragmentById(containerId.value)
                fragment?.apply {
                    onPause()
                    onStop()
                }
            }
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { fragmentContainerView },
        update = {}
    )
}

fun NavHostController.containsBackStackEntry(navBackStackEntry: NavBackStackEntry): Boolean {
    return this.currentBackStack.value.map { it.id }.contains(navBackStackEntry.id)
}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_START) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state.value
}
