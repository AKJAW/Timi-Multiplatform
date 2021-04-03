package com.example.timicompose.stopwatch.composition

import com.example.timicompose.common.domain.KlockTimestampProvider
import com.example.timicompose.common.domain.TimestampProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class StopwatchModule {

    @Binds
    abstract fun bindTimestampProvider(klockTimestampProvider: KlockTimestampProvider): TimestampProvider
}
