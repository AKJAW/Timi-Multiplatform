package com.akjaw.stopwatch.composition

import com.akjaw.core.common.composition.BackgroundDispatcherQualifier
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.stopwatch.view.StopwatchScreenCreator
import com.akjaw.stopwatch.view.StopwatchScreenCreatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StopwatchModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: StopwatchScreenCreatorImpl): StopwatchScreenCreator

    companion object {

        @Provides
        @Singleton
        fun provideStopwatchListOrchestrator(
            stopwatchStateHolderFactory: StopwatchStateHolderFactory,
            @BackgroundDispatcherQualifier dispatcher: CoroutineDispatcher
        ): StopwatchListOrchestrator {
            return StopwatchListOrchestrator(
                stopwatchStateHolderFactory = stopwatchStateHolderFactory,
                scope = CoroutineScope(SupervisorJob() + dispatcher)
            )
        }
    }
}
