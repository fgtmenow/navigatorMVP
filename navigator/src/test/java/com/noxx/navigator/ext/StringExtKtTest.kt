package com.noxx.navigator.ext

import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtKtTest {

    @Test
    fun empty_args() {
        val res = buildFullRoute("app/loader", emptyList())
        assertEquals("app/loader", res)
    }

    @Test
    fun with_args() {
        val res = buildFullRoute(
            root = "app/loader",
            arguments = listOf(
                navArgument("arg1") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("arg2") {
                    type = NavType.IntArrayType
                    nullable = false
                },
                navArgument("arg3") {
                    type = NavType.LongType
                    nullable = false
                },
                navArgument("arg4") {
                    type = NavType.LongArrayType
                    nullable = false
                },
                navArgument("arg5") {
                    type = NavType.BoolType
                    nullable = false
                },
                navArgument("arg6") {
                    type = NavType.BoolArrayType
                    nullable = false
                }

            )
        )
        assertEquals(
            "app/loader?arg1={arg1}&arg2={arg2}&arg3={arg3}&arg4={arg4}&arg5={arg5}&arg6={arg6}",
            res
        )
    }
}
