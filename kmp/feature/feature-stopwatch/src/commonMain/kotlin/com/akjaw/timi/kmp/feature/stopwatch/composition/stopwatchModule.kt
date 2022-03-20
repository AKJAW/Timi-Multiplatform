package com.akjaw.timi.kmp.feature.stopwatch.composition

import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.feature.stopwatch.presentation.StopwatchViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val stopwatchModule = module {
    factory { ElapsedTimeCalculator(get()) }
    factory { TimestampMillisecondsFormatter() }
    factory { StopwatchStateHolderFactory(get(), get(), get()) }
    factory { StopwatchStateCalculator(get(), get()) }
    single {
        StopwatchListOrchestrator(
            stopwatchStateHolderFactory = get(),
            scope = CoroutineScope(SupervisorJob() + Dispatchers.Default) // TODO make dispatchers abstractions
        )
    }
    factory { StopwatchViewModel(get(), get()) }
}
