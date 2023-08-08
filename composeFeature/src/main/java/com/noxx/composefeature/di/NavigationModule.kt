package com.noxx.composefeature.di

import com.noxx.composefeature.screens.composeWithPrimitive.ComposePrimitiveScreen
import com.noxx.composefeature.screens.resultCompose.ResultComposeScreen
import com.noxx.composefeature.screens.startCompose.StartComposeScreen
import com.noxx.navigation.destinations.compose.ComposeWithPrimitiveDestination
import com.noxx.navigation.destinations.compose.ResultComposeDestination
import com.noxx.navigation.destinations.compose.StartComposeDestination
import com.noxx.navigator.model.FeatureDestinations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @ActivityScoped
    @Provides
    @IntoSet
    fun provideDestinations(): FeatureDestinations {
        return FeatureDestinations(
            listOf(
                StartComposeDestination to { _, _ -> StartComposeScreen() },
                ComposeWithPrimitiveDestination to { _, _ -> ComposePrimitiveScreen() },
                ResultComposeDestination to { _, _ -> ResultComposeScreen() }
            )
        )
    }
}
