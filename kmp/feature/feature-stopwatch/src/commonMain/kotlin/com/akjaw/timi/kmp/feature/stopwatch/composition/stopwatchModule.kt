package com.akjaw.timi.kmp.feature.stopwatch.composition

import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import org.koin.dsl.module

val stopwatchModule = module {
    factory { ElapsedTimeCalculator(get()) }
    factory { TimestampMillisecondsFormatter() }
}
