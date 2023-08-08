package com.noxx.navigation.destinations.fragment

import android.os.Parcelable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.noxx.navigator.model.Destination
import com.noxx.navigator.navRoute.navRoute
import kotlinx.parcelize.Parcelize

object FragmentWithPrimitiveDestination : Destination() {

    override val navRoot: String = "app/fragmentPrimitiveArgs"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ARG1) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(ARG2) {
                type = NavType.LongType
                defaultValue = -1
                nullable = false
            }
        )

    fun createRoute(arg1: String, arg2: Long) = navRoute {
        root(navRoot)
        param(ARG1 to arg1)
        param(ARG2 to arg2)
    }

    @Parcelize
    data class DataParcelableTestArgument(val test: String) : Parcelable

    const val ARG1 = "ARG1"
    const val ARG2 = "ARG2"
}
