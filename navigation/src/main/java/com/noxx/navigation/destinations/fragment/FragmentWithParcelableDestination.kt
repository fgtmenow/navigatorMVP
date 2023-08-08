package com.noxx.navigation.destinations.fragment

import android.os.Parcelable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.noxx.navigator.model.Destination
import com.noxx.navigator.navRoute.navRoute
import kotlinx.parcelize.Parcelize

object FragmentWithParcelableDestination : Destination() {

    override val navRoot: String = "app/fragmentParcelableArgs"

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
            },
            navArgument(ARG3) {
                type = NavType.ParcelableType(DataParcelableTestArgument::class.java)
                defaultValue = -1
                nullable = false
            }
        )

    fun createRoute(arg1: String, arg2: Long, parcel: DataParcelableTestArgument) = navRoute {
        root(navRoot)
        param(ARG1 to arg1)
        param(ARG2 to arg2)
        param(ARG3 to parcel)
    }

    @Parcelize
    data class DataParcelableTestArgument(val test: String) : Parcelable

    const val ARG1 = "ARG1"
    const val ARG2 = "ARG2"
    const val ARG3 = "ARG3"
}
