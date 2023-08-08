package com.noxx.navigator.ext

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noxx.navigator.navRoute.navRoute
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavRouteBuilderKtTest {

    @Test
    fun test() {
        val route = navRoute {
            root("app/compose1")
            param("ARG1" to "test")
            param("ARG2" to 11)
            param("ARG3" to 13L)
            param("ARG4" to true)
        }
        assertEquals(
            "app/compose1?ARG1=test&ARG2=11&ARG3=13&ARG4=true",
            route
        )
    }
}
