package com.akjaw.timi.kmp.feature.stopwatch.composition

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.presentation.StopwatchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val stopwatchModule = module {
    factory { ElapsedTimeCalculator(get()) }
    factory { StopwatchStateHolderFactory(get(), get(), get()) }
    factory { StopwatchStateCalculator(get(), get()) }
    single {
        val dispatcherProvider: DispatcherProvider = get()
        StopwatchListOrchestrator(
            stopwatchStateHolderFactory = get(),
            scope = CoroutineScope(SupervisorJob() + dispatcherProvider.default)
        )
    }
    factory { StopwatchViewModel(get(), get()) }
}
