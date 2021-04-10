package com.akjaw.core.common.domain.model

//TODO maybe :stopwatch-api?
data class TimestampMilliseconds(val value: Long) {

    infix operator fun plus(other: TimestampMilliseconds) =
        TimestampMilliseconds(this.value + other.value)

    infix operator fun minus(other: TimestampMilliseconds) =
        TimestampMilliseconds(this.value - other.value)
}

fun Number.toTimestampMilliseconds() = TimestampMilliseconds(this.toLong())
