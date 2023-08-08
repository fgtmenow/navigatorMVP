package com.noxx.navigator.generator.parser

import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationGraphParserTest {

    val input = listOf(
        """
            <?xml version="1.0" encoding="utf-8"?>
            <navigation
                xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                android:id="@+id/navigation_main"
                app:startDestination="@id/navigation_home">

                <include app:graph="@navigation/navigation_home" />
                <include app:graph="@navigation/navigation_detail" />

            </navigation>
        """.trimIndent(),
        """
            <?xml version="1.0" encoding="utf-8"?>
            <navigation xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                xmlns:tools="http://schemas.android.com/tools"
                android:id="@+id/navigation_detail"
                app:startDestination="@id/detailFragment">

                <fragment
                    android:id="@+id/detailFragment"
                    android:name="com.example.movieapp.detail.ui.DetailFragment"
                    android:label="@string/navigation_detail_label"
                    tools:layout="@layout/fragment_detail">

                    <argument
                        android:name="movieId"
                        android:defaultValue="0"
                        app:argType="integer" />
                        
                          <argument
                        android:name="movieId2"
                       
                        app:argType="integer" />
                        
                            <argument
                        android:name="movieId3"
                        android:defaultValue=""
                        app:argType="integer" />

                    <deepLink
                        android:id="@+id/detailDeepLink"
                        app:uri="movieapp://detail?movieId={movieId}" />
                </fragment>

            </navigation>
        """.trimIndent()
    )

    @Test
    fun parse() {
        val fragments = input
            .map {
                NavigationGraphParser.parse(it)
            }
            .flatMap { it.fragments }
        assertEquals(fragments.size, 1)
    }
}
