package com.akjaw.stopwatch.composition

import com.akjaw.stopwatch.view.StopwatchScreenCreator
import com.akjaw.stopwatch.view.StopwatchScreenCreatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StopwatchModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: StopwatchScreenCreatorImpl): StopwatchScreenCreator
}