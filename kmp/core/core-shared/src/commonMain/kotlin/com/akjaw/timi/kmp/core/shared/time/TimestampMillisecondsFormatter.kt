package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds

class TimestampMillisecondsFormatter {

    fun formatWithMilliseconds(timestamp: TimestampMilliseconds): String {
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

    fun formatWithoutMilliseconds(timestamp: TimestampMilliseconds): String {
        val millisecondsOnly = timestamp.value % 1000
        val millisecondsLong = if (millisecondsOnly >= 500) {
            timestamp.value + (1000 - millisecondsOnly)
        } else {
            timestamp.value - ((1000 - millisecondsOnly) % 1000)
        }
        val seconds = millisecondsLong / 1000
        val secondsFormatted = (seconds % 60).pad(2)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(2)
        val hours = minutes / 60
        return if (hours > 0) {
            val hoursFormatted = (minutes / 60).pad(2)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}
