package com.akjaw.details.presenter.calendar

import com.soywiz.klock.DateTime
import com.soywiz.klock.MonthSpan

class CalendarDaysCalculator {

    companion object {
        private const val MONDAY_START_OF_WEEK_OFFSET = 1
        private const val DAYS_IN_A_WEEK = 7
    }

    private data class MiddleRows(
        val second: List<Int>,
        val third: List<Int>,
        val fourth: List<Int>,
        val fifth: List<Int>,
    )

    fun calculate(currentMonth: DateTime): List<List<Day>> {
        val firstRow = calculateFirstRow(currentMonth)
        val middleRows = calculateMiddleRows(currentMonth, firstRow)
        val lastRow = calculateLastRow(currentMonth, middleRows.fifth)
        return listOf(
            firstRow,
            middleRows.second.map { Day(it.toString()) },
            middleRows.third.map { Day(it.toString()) },
            middleRows.fourth.map { Day(it.toString()) },
            middleRows.fifth.map { Day(it.toString()) },
            lastRow,
        )
    }

    private fun calculateFirstRow(currentMonth: DateTime): List<Day> {
        val previousMonth = currentMonth.minus(MonthSpan(1))
        val numberOfDaysInPreviousMonth = previousMonth.getNumberOfDaysInMonth()
        val weekPositionOfCurrentMonthFirstDay =
            getPositionInRelationToWeek(currentMonth.copyDayOfMonth(dayOfMonth = 1))
        val firstRowStartDay =
            numberOfDaysInPreviousMonth - weekPositionOfCurrentMonthFirstDay

        val previousMonthDayRange = (firstRowStartDay..numberOfDaysInPreviousMonth)
        val previousMonthDays = createFromDayRange(previousMonthDayRange, previousMonth)

        val nextMonthRemainingDays = DAYS_IN_A_WEEK - previousMonthDays.count()
        val currentMonthDayRange = (1..nextMonthRemainingDays)
        val currentMonthDays = createFromDayRange(currentMonthDayRange, currentMonth)

        return previousMonthDays + currentMonthDays
    }

    private fun getPositionInRelationToWeek(currentMonth: DateTime): Int {
        val dayOfWeekWithOffset = currentMonth.dayOfWeekInt - MONDAY_START_OF_WEEK_OFFSET
        val indexedDayOfMonth = (DAYS_IN_A_WEEK + dayOfWeekWithOffset) % 7
        return indexedDayOfMonth - MONDAY_START_OF_WEEK_OFFSET
    }

    private fun calculateMiddleRows(
        currentMonth: DateTime,
        firstRow: List<Day>,
    ): MiddleRows {
        val firstDayOfSecondRow = firstRow.last().value.toInt() + 1
        val secondRow = (firstDayOfSecondRow until firstDayOfSecondRow + DAYS_IN_A_WEEK)

        val firstDayOfThirdRow = secondRow.last() + 1
        val thirdRow = (firstDayOfThirdRow until firstDayOfThirdRow + DAYS_IN_A_WEEK)

        val firstDayOfFourthRow = thirdRow.last() + 1
        val fourthRow = (firstDayOfFourthRow until firstDayOfFourthRow + DAYS_IN_A_WEEK)

        val firstDayOfFifthRow = fourthRow.last() + 1
        val numberOfDaysInCurrentMonth = currentMonth.getNumberOfDaysInMonth()
        val fifthRow =
            if (firstDayOfFifthRow + DAYS_IN_A_WEEK > numberOfDaysInCurrentMonth) {
                getDaysForMonthAndRemainingNextMonthDays(
                    rowStartingDay = firstDayOfFifthRow,
                    numberOfDaysInMonth = numberOfDaysInCurrentMonth
                )
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

    private fun calculateLastRow(currentMonth: DateTime, fifthRow: List<Int>): List<Day> {
        val lastRowStartingDay = fifthRow.last() + 1
        val nextMonth = currentMonth.plus(MonthSpan(1))
        return if (lastRowStartingDay >= 28) {
            val numberOfDaysInCurrentMonth = currentMonth.getNumberOfDaysInMonth()
            val currentMontDayRange = lastRowStartingDay..numberOfDaysInCurrentMonth
            val currentMonthDays = createFromDayRange(currentMontDayRange, currentMonth)

            val nextMonthRemainingDays = DAYS_IN_A_WEEK - currentMontDayRange.count()
            val nextMonthDays = createFromDayRange(1..nextMonthRemainingDays, nextMonth)
            return currentMonthDays + nextMonthDays
        } else {
            val nextMonthDayRange = (lastRowStartingDay until lastRowStartingDay + DAYS_IN_A_WEEK)
            createFromDayRange(nextMonthDayRange, nextMonth)
        }
    }

    private fun getDaysForMonthAndRemainingNextMonthDays(
        rowStartingDay: Int,
        numberOfDaysInMonth: Int
    ): List<Int> {
        val monthDays = rowStartingDay..numberOfDaysInMonth
        val nextMonthRemainingDays = DAYS_IN_A_WEEK - monthDays.count()
        return monthDays + (1..nextMonthRemainingDays)
    }

    private fun DateTime.getNumberOfDaysInMonth(): Int {
        return month.days(year)
    }

    private fun createFromDayRange(
        dayRange: IntRange,
        monthDateTime: DateTime
    ): List<Day> {
        val monthNumber = monthDateTime.month.index1
        val monthYear = monthDateTime.yearInt
        return dayRange.map { dayNumber ->
            Day(dayNumber.toString(), monthNumber, monthYear)
        }
    }
}
