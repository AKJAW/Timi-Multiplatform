package com.akjaw.stopwatch.composition

import com.akjaw.core.common.composition.BackgroundDispatcherQualifier
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.stopwatch.view.StopwatchScreenCreator
import com.akjaw.stopwatch.view.StopwatchScreenCreatorImpl
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StopwatchModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: StopwatchScreenCreatorImpl): StopwatchScreenCreator

    companion object : KoinComponent {

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

        @Provides
        @Singleton
        fun provideElapsedTimeCalculator(): ElapsedTimeCalculator = get()

        @Provides
        @Singleton
        fun provideTimestampMillisecondsFormatter(): TimestampMillisecondsFormatter = get()
    }
}