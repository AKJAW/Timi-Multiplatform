package com.akjaw.timi.kmp.feature.task.dependency.detail

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import io.kotest.matchers.shouldBe

internal fun createCalendarViewModel(
    timestampProvider: TimestampProvider
) = CalendarViewModel(
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)

fun List<CalendarDay>.rowWithOneMonthHasCorrectDates(
    month: Int,
    year: Int
) {
    get(0).hasCorrectDate(month, year)
    get(1).hasCorrectDate(month, year)
    get(2).hasCorrectDate(month, year)
    get(3).hasCorrectDate(month, year)
    get(4).hasCorrectDate(month, year)
    get(5).hasCorrectDate(month, year)
    get(6).hasCorrectDate(month, year)
}

fun List<CalendarDay>.rowWithTwoMonthsHasCorrectDates(
    monthsAndYears: List<Pair<Int, Int>>
) {
    get(0).hasCorrectDate(monthsAndYears[0].first, monthsAndYears[0].second)
    get(1).hasCorrectDate(monthsAndYears[1].first, monthsAndYears[1].second)
    get(2).hasCorrectDate(monthsAndYears[2].first, monthsAndYears[2].second)
    get(3).hasCorrectDate(monthsAndYears[3].first, monthsAndYears[3].second)
    get(4).hasCorrectDate(monthsAndYears[4].first, monthsAndYears[4].second)
    get(5).hasCorrectDate(monthsAndYears[5].first, monthsAndYears[5].second)
    get(6).hasCorrectDate(monthsAndYears[6].first, monthsAndYears[6].second)
}

fun CalendarDay.hasCorrectDate(
    month: Int,
    year: Int
) {
    this.month shouldBe month
    this.year shouldBe year
}

fun List<List<CalendarDay>>.rowDaysEqual(
    rowIndex: Int,
    expectedDays: List<Int>
) {
    val rowDays = this[rowIndex].map { it.day }
    rowDays shouldBe expectedDays
}
