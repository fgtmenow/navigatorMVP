package com.noxx.fragmentfeature.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.noxx.fragmentfeature.screens.fragment2.FeatureFragment2
import com.noxx.fragmentfeature.screens.fragmentSaveState.FragmentSaveState
import com.noxx.fragmentfeature.screens.fragmentWithParcelable.FragmentWithParcelable
import com.noxx.fragmentfeature.screens.fragmentWithPrimitive.FragmentWithPrimitive
import com.noxx.fragmentfeature.screens.resultApiFragment.ResultApiFragment
import com.noxx.fragmentfeature.screens.resultFragment.ResultFragment
import com.noxx.navigation.destinations.fragment.Fragment2Destination
import com.noxx.navigation.destinations.fragment.FragmentSaveStateDestination
import com.noxx.navigation.destinations.fragment.FragmentWithParcelableDestination
import com.noxx.navigation.destinations.fragment.FragmentWithPrimitiveDestination
import com.noxx.navigation.destinations.fragment.ResultApiFragmentDestination
import com.noxx.navigation.destinations.fragment.ResultFragmentDestination
import com.noxx.navigator.composable.FragmentContainer
import com.noxx.navigator.model.FeatureDestinations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @ActivityScoped
    @Provides
    @IntoSet
    fun provideDestinations(
        @ActivityContext context: Context
    ): FeatureDestinations {
        val fragmentManager = (context as FragmentActivity).supportFragmentManager
        return FeatureDestinations(
            listOf(
                FragmentSaveStateDestination to { navHostController, navBackStackEntry ->
                    FragmentContainer<FragmentSaveState>(
                        fragmentManager,
                        navHostController,
                        navBackStackEntry
                    )
                },
                Fragment2Destination to { navHostController, navBackStackEntry ->
                    FragmentContainer<FeatureFragment2>(
                        fragmentManager,
                        navHostController,
                        navBackStackEntry
                    )
                },
                ResultApiFragmentDestination to { navHostController, navBackStackEntry ->
                    FragmentContainer<ResultApiFragment>(
                        fragmentManager,
                        navHostController,
                        navBackStackEntry
                    )
                },
                ResultFragmentDestination to { navHostController, navBackStackEntry ->
                    FragmentContainer<ResultFragment>(
                        fragmentManager,
                        navHostController,
                        navBackStackEntry
                    )
                },
                FragmentWithPrimitiveDestination to { navHostController, navBackStackEntry ->
                    FragmentContainer<FragmentWithPrimitive>(
                        fragmentManager,
                        navHostController,
                        navBackStackEntry
                    )
                },
                FragmentWithParcelableDestination to { navHostController, navBackStackEntry ->
                    FragmentContainer<FragmentWithParcelable>(
                        fragmentManager,
                        navHostController,
                        navBackStackEntry
                    )
                }
            )
        )
    }
}
