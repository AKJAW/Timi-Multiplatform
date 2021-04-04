package com.example.timicompose.stopwatch.composition

import com.example.timicompose.common.composition.DispatcherQualifiers
import com.example.timicompose.common.domain.KlockTimestampProvider
import com.example.timicompose.common.domain.TimestampProvider
import com.example.timicompose.stopwatch.domain.StopwatchListOrchestrator
import com.example.timicompose.stopwatch.domain.StopwatchStateHolderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class StopwatchModule {

    @Binds
    abstract fun bindTimestampProvider(klockTimestampProvider: KlockTimestampProvider): TimestampProvider

    companion object {

        @Provides
        fun provideStopwatchListOrchestrator(
            stopwatchStateHolderFactory: StopwatchStateHolderFactory,
            @DispatcherQualifiers dispatcher: CoroutineDispatcher
        ): StopwatchListOrchestrator {
            return StopwatchListOrchestrator(
                stopwatchStateHolderFactory = stopwatchStateHolderFactory,
                scope = CoroutineScope(SupervisorJob() + dispatcher)
            )
        }
    }
}
