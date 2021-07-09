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
        val firstRow = calculateFirstRow(currentMonth)
        val lastRow = calculateLastRow(currentMonth)
        return listOf(
            firstRow.map { Day(it.toString()) },
            listOf(5..11).toDays(),
            listOf(12..18).toDays(),
            listOf(19..25).toDays(),
            lastRow.map { Day(it.toString()) },
        )
    }

    private fun calculateFirstRow(currentMonth: DateTime): List<Int> {
        val numberOfDaysInPreviousMonth = getNumberOfDaysInPreviousMonth(currentMonth)
        val firstDayWeekPosition =
            getPositionInRelationToWeek(currentMonth.copyWithFirstDayOfMonth())
        val indexAdjustment = 1
        val previousMonthStartDay =
            numberOfDaysInPreviousMonth - firstDayWeekPosition + indexAdjustment

        val previousMonthDays = (previousMonthStartDay..numberOfDaysInPreviousMonth).toList()

        val numberOfDaysLeftInFirstWeekOfCurrentMonth = DAYS_IN_A_WEEK - previousMonthDays.count()
        val currentMonthDays = 1..numberOfDaysLeftInFirstWeekOfCurrentMonth

        return previousMonthDays + currentMonthDays
    }

    private fun calculateLastRow(currentMonth: DateTime): List<Int> {
        val numberOfDaysInCurrentMonth = getNumberOfDaysInMonth(currentMonth)
        val firstDayWeekPosition =
            getPositionInRelationToWeek(currentMonth.copyWithFirstDayOfMonth())

        return if (firstDayWeekPosition + numberOfDaysInCurrentMonth <= 28) {
            (8..14).toList()
        } else {
            val lastDayWeekPosition =
                getPositionInRelationToWeek(currentMonth.copyWithLastDayOfMonth())
            val start = numberOfDaysInCurrentMonth - lastDayWeekPosition
            val currentMonthDays = (start..numberOfDaysInCurrentMonth).toList()
            val end = DAYS_IN_A_WEEK - currentMonthDays.count()

            if (firstDayWeekPosition + numberOfDaysInCurrentMonth <= 35) {
                val startOfTheLastRow = end + 1
                (startOfTheLastRow until startOfTheLastRow + DAYS_IN_A_WEEK).toList()
            } else {
                val nextMonthDays = (1..end)
                currentMonthDays + nextMonthDays
            }
        }
    }

    private fun getPositionInRelationToWeek(currentMonth: DateTime): Int {
        return currentMonth.dayOfWeekIntAdjustedForMonday()
    }

    private fun getNumberOfDaysInPreviousMonth(currentMonth: DateTime): Int {
        val previousMonth = currentMonth.minus(MonthSpan(1))
        return getNumberOfDaysInMonth(previousMonth)
    }

    private fun getNumberOfDaysInMonth(currentMonth: DateTime): Int {
        return currentMonth.month.days(currentMonth.year)
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
