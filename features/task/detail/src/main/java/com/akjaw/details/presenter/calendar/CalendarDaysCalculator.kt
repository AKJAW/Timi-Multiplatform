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
            firstRow.map { Day(it.toString()) },
            middleRows.second.map { Day(it.toString()) },
            middleRows.third.map { Day(it.toString()) },
            middleRows.fourth.map { Day(it.toString()) },
            middleRows.fifth.map { Day(it.toString()) },
            lastRow.map { Day(it.toString()) },
        )
    }

    private fun calculateFirstRow(currentMonth: DateTime): List<Int> {
        val numberOfDaysInPreviousMonth = getNumberOfDaysInPreviousMonth(currentMonth)
        val weekPositionOfCurrentMonthFirstDay =
            getPositionInRelationToWeek(currentMonth.copyDayOfMonth(dayOfMonth = 1))
        val firstRowStartDay =
            numberOfDaysInPreviousMonth - weekPositionOfCurrentMonthFirstDay

        return getDaysForMonthAndRemainingNextMonthDays(
            rowStartingDay = firstRowStartDay,
            numberOfDaysInMonth = numberOfDaysInPreviousMonth
        )
    }

    private fun getNumberOfDaysInPreviousMonth(currentMonth: DateTime): Int {
        val previousMonth = currentMonth.minus(MonthSpan(1))
        return previousMonth.getNumberOfDaysInMonth()
    }

    private fun getPositionInRelationToWeek(currentMonth: DateTime): Int {
        val dayOfWeekWithOffset = currentMonth.dayOfWeekInt - MONDAY_START_OF_WEEK_OFFSET
        val indexedDayOfMonth = (DAYS_IN_A_WEEK + dayOfWeekWithOffset) % 7
        return indexedDayOfMonth - MONDAY_START_OF_WEEK_OFFSET
    }

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

    private fun calculateLastRow(currentMonth: DateTime, fifthRow: List<Int>): List<Int> {
        val lastRowStartingDay = fifthRow.last() + 1
        return if (lastRowStartingDay >= 28) {
            val numberOfDaysInCurrentMonth = currentMonth.getNumberOfDaysInMonth()
            getDaysForMonthAndRemainingNextMonthDays(
                rowStartingDay = lastRowStartingDay,
                numberOfDaysInMonth = numberOfDaysInCurrentMonth
            )
        } else {
            (lastRowStartingDay until lastRowStartingDay + DAYS_IN_A_WEEK).toList()
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
}
