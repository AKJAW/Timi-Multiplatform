package com.akjaw.timi.kmp.feature.stopwatch.domain.utilities

import com.akjaw.core.common.domain.model.TimestampMilliseconds

class TimestampMillisecondsFormatter {

    companion object {
        const val DEFAULT_TIME = "00:00:000"
    }

    fun format(timestamp: TimestampMilliseconds): String {
        val millisecondsLong = timestamp.value
        val millisecondsFormatted = (millisecondsLong % 1000).pad(3)
        val seconds = millisecondsLong / 1000
        val secondsFormatted = (seconds % 60).pad(2)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(2)
        val hours = minutes / 60
        return if (hours > 0) {
            val hoursFormatted = (minutes / 60).pad(2)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}
