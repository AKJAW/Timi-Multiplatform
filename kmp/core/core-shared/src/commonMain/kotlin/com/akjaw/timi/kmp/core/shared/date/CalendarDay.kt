package com.akjaw.timi.kmp.core.shared.date

// TODO rename
// TODO add time?
// TODO remove default values?
data class CalendarDay(
    val day: Int,
    val month: Int = 0,
    val year: Int = 0
)

fun CalendarDay.format(): String = "$day.$month.$year"
