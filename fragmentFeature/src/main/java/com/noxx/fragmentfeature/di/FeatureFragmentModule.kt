package com.noxx.fragmentfeature.di

import com.noxx.fragmentfeature.repo.DummyDep
import com.noxx.fragmentfeature.repo.DummyDepImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FeatureFragmentModule {

    @Provides
    fun getDummyDep(): DummyDep {
        return DummyDepImpl()
    }
}
