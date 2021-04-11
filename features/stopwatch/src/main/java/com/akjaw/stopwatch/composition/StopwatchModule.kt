package com.akjaw.stopwatch.composition

import com.akjaw.core.common.composition.DispatcherQualifiers
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.stopwatch.view.StopwatchScreenCreator
import com.akjaw.stopwatch.view.StopwatchScreenCreatorImpl
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
internal abstract class StopwatchModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: StopwatchScreenCreatorImpl): StopwatchScreenCreator

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
