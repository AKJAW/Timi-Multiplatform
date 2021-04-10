package com.akjaw.stopwatch.domain

import com.akjaw.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import javax.inject.Inject

class StopwatchStateHolderFactory @Inject constructor(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
) {

    fun create(): StopwatchStateHolder {
        return StopwatchStateHolder(
            stopwatchStateCalculator = stopwatchStateCalculator,
            elapsedTimeCalculator = elapsedTimeCalculator,
            timestampMillisecondsFormatter = timestampMillisecondsFormatter
        )
    }
}
