package com.noxx.navmvp.di

import com.noxx.navigator.model.AppDestinations
import com.noxx.navigator.model.FeatureDestinations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class AppActivityModule {

    @Provides
    @ActivityScoped
    fun getDirections(
        featureDestinationsSet: Set<@JvmSuppressWildcards FeatureDestinations>
    ): AppDestinations {
        return AppDestinations(
            featureDestinationsSet.flatMap { it.destinations }
        )
    }
}
