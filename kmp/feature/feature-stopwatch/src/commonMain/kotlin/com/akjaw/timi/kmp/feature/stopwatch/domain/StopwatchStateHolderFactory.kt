package com.akjaw.timi.kmp.feature.stopwatch.domain

import com.akjaw.timi.kmp.core.shared.time.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator

internal class StopwatchStateHolderFactory(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) {

    fun create(): StopwatchStateHolder {
        return StopwatchStateHolder(
            stopwatchStateCalculator = stopwatchStateCalculator,
            elapsedTimeCalculator = elapsedTimeCalculator,
            timestampMillisecondsFormatter = timestampMillisecondsFormatter
        )
    }
}
