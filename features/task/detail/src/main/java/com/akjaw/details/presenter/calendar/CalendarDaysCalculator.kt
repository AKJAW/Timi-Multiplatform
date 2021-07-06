package com.akjaw.details.presenter.calendar

import com.soywiz.klock.DateTime
import com.soywiz.klock.MonthSpan

class CalendarDaysCalculator {

    companion object {
        private const val MONDAY_START_OF_WEEK_OFFSET = 1
        private const val DAYS_IN_A_WEEK = 7
    }

    // TODO encapsulate the months in a separate data structure?
    fun calculate(currentMonth: DateTime): List<List<Day>> {
        val lastDayOfMonth = currentMonth.copyWithLastDayOfMonth()
        val firstRow = calculateFirstRow(currentMonth)
        return listOf(
            firstRow.map { Day(it.toString()) },
            listOf(5..11).toDays(),
            listOf(12..18).toDays(),
            listOf(19..25).toDays(),
            listOf(26..31, 1..6).toDays(),
        )
    }

    private fun calculateFirstRow(currentMonth: DateTime): List<Int> {
        val numberOfDaysInPreviousMonth = getNumberOfDaysInPreviousMonth(currentMonth)
        val firstDayOfWeekPosition = getStartingDayPositionInRelationToWeek(currentMonth)
        val indexAdjustment = 1
        val previousMonthStartDay =
            numberOfDaysInPreviousMonth - firstDayOfWeekPosition + indexAdjustment

        val previousMonthDays = (previousMonthStartDay..numberOfDaysInPreviousMonth).toList()

        val numberOfDaysLeftInFirstWeekOfCurrentMonth = DAYS_IN_A_WEEK - previousMonthDays.count()
        val currentMonthDays = 1..numberOfDaysLeftInFirstWeekOfCurrentMonth

        return previousMonthDays + currentMonthDays
    }

    private fun getStartingDayPositionInRelationToWeek(currentMonth: DateTime): Int {
        val firstDayOfMonth = currentMonth.copyWithFirstDayOfMonth()
        return firstDayOfMonth.dayOfWeekIntAdjustedForMonday()
    }

    private fun getNumberOfDaysInPreviousMonth(currentMonth: DateTime): Int {
        val previousMonth = currentMonth.minus(MonthSpan(1))
        return previousMonth.month.days(previousMonth.year)
    }

    private fun DateTime.copyWithFirstDayOfMonth(): DateTime =
        copyDayOfMonth(dayOfMonth = 1)

    private fun DateTime.copyWithLastDayOfMonth(): DateTime {
        val numberOfDays = month.days(year)
        return copyDayOfMonth(dayOfMonth = numberOfDays)
    }

    private fun DateTime.dayOfWeekIntAdjustedForMonday(): Int {
        val dayOfWeekWithOffset = this.dayOfWeekInt - MONDAY_START_OF_WEEK_OFFSET
        return (DAYS_IN_A_WEEK + dayOfWeekWithOffset) % 7
    }
}
