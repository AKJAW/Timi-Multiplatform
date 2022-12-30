package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds

class TimestampMillisecondsFormatter {

    fun formatWithMilliseconds(timestamp: TimestampMilliseconds): String =
        timestamp.value.formatTime(showMilliseconds = true)

    fun formatWithoutMilliseconds(timestamp: TimestampMilliseconds): String {
        val millisecondsLong = timestamp.roundMilliseconds()
        return millisecondsLong.formatTime(showMilliseconds = false)
    }

    private fun TimestampMilliseconds.roundMilliseconds(): Long {
        val millisecondsOnly = this.value % 1000
        return if (millisecondsOnly >= 500) {
            value + (1000 - millisecondsOnly)
        } else {
            value - ((1000 - millisecondsOnly) % 1000)
        }
    }

    private fun Long.formatTime(showMilliseconds: Boolean): String {
        val seconds = this / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return buildString {
            if (hours > 0) {
                val hoursFormatted = (minutes / 60).pad(2)
                append(hoursFormatted)
                append(":")
            }

            val minutesFormatted = (minutes % 60).pad(2)
            append(minutesFormatted)
            append(":")

            val secondsFormatted = (seconds % 60).pad(2)
            append(secondsFormatted)

            if (hours == 0L && showMilliseconds) {
                val millisecondsFormatted = (this@formatTime % 1000).pad(3)
                append(":$millisecondsFormatted")
            }
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}
