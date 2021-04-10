package com.akjaw.stopwatch.composition

import com.akjaw.core.common.composition.DispatcherQualifiers
import com.akjaw.core.common.domain.KlockTimestampProvider
import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.stopwatch.domain.StopwatchStateHolderFactory
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
