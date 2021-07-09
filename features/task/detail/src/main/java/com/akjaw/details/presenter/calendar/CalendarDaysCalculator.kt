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
        val middleRows = calculateMiddleRows(currentMonth, firstRow)
        val lastRow = calculateLastRow(currentMonth, middleRows.fifth)
        return listOf(
            firstRow.map { Day(it.toString()) },
            middleRows.second.map { Day(it.toString()) },
            middleRows.third.map { Day(it.toString()) },
            middleRows.fourth.map { Day(it.toString()) },
            middleRows.fifth.map { Day(it.toString()) },
            lastRow.map { Day(it.toString()) },
        )
    }

    data class MiddleRows(
        val second: List<Int>,
        val third: List<Int>,
        val fourth: List<Int>,
        val fifth: List<Int>,
    )

    private fun calculateMiddleRows(
        currentMonth: DateTime,
        firstRow: List<Int>,
    ): MiddleRows {
        val firstDayOfSecondRow = firstRow.last() + 1
        val secondRow = (firstDayOfSecondRow until firstDayOfSecondRow + DAYS_IN_A_WEEK)

        val firstDayOfThirdRow = secondRow.last() + 1
        val thirdRow = (firstDayOfThirdRow until firstDayOfThirdRow + DAYS_IN_A_WEEK)

        val firstDayOfFourthRow = thirdRow.last() + 1
        val fourthRow = (firstDayOfFourthRow until firstDayOfFourthRow + DAYS_IN_A_WEEK)

        val firstDayOfFifthRow = fourthRow.last() + 1
        val numberOfDaysInCurrentMonth = getNumberOfDaysInMonth(currentMonth)
        val fifthRow =
            if (firstDayOfFifthRow + DAYS_IN_A_WEEK > numberOfDaysInCurrentMonth) {
                val currentMonthDays = firstDayOfFifthRow..numberOfDaysInCurrentMonth
                val remainingDays = DAYS_IN_A_WEEK - currentMonthDays.count()
                currentMonthDays + (1..remainingDays)
            } else {
                firstDayOfFifthRow until firstDayOfFifthRow + DAYS_IN_A_WEEK
            }

        return MiddleRows(
            second = secondRow.toList(),
            third = thirdRow.toList(),
            fourth = fourthRow.toList(),
            fifth = fifthRow.toList()
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

    private fun calculateLastRow(currentMonth: DateTime, fifthRow: List<Int>): List<Int> {
        val lastRowStartingDay = fifthRow.last() + 1
        return if (fifthRow.last() > 28) {
            val numberOfDaysInCurrentMonth = getNumberOfDaysInMonth(currentMonth)
            val currentMonthDays = lastRowStartingDay..numberOfDaysInCurrentMonth
            val remainingDays = DAYS_IN_A_WEEK - currentMonthDays.count()
            currentMonthDays.toList() + (1..remainingDays)
        } else {
            (lastRowStartingDay until lastRowStartingDay + DAYS_IN_A_WEEK).toList()
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
