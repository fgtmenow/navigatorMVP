package com.noxx.composefeature.di

import com.noxx.composefeature.repo.DummyDep
import com.noxx.composefeature.repo.DummyDepImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ComposeModule {

    @Provides
    fun getDummyDep(): DummyDep {
        return DummyDepImpl()
    }
}
