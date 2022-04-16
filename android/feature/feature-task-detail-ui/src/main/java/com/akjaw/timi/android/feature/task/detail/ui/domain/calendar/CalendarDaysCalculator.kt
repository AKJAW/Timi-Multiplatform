package com.akjaw.timi.android.feature.task.detail.ui.domain.calendar

import com.soywiz.klock.DateTime
import com.soywiz.klock.MonthSpan

class CalendarDaysCalculator() {

    companion object {
        private const val MONDAY_START_OF_WEEK_OFFSET = 1
        private const val DAYS_IN_A_WEEK = 7
    }

    private data class MiddleRows(
        val second: List<CalendarDay>,
        val third: List<CalendarDay>,
        val fourth: List<CalendarDay>,
        val fifth: List<CalendarDay>,
    )

    fun calculate(currentMonth: DateTime): List<List<CalendarDay>> {
        val firstRow = calculateFirstRow(currentMonth)
        val middleRows = calculateMiddleRows(currentMonth, firstRow)
        val lastRow = calculateLastRow(currentMonth, middleRows.fifth)
        return listOf(
            firstRow,
            middleRows.second,
            middleRows.third,
            middleRows.fourth,
            middleRows.fifth,
            lastRow,
        )
    }

    private fun calculateFirstRow(currentMonth: DateTime): List<CalendarDay> {
        val previousMonth = currentMonth.minus(MonthSpan(1))
        val numberOfDaysInPreviousMonth = previousMonth.getNumberOfDaysInMonth()
        val weekPositionOfCurrentMonthFirstDay =
            getPositionInRelationToWeek(currentMonth.copyDayOfMonth(dayOfMonth = 1))
        val firstRowStartDay =
            numberOfDaysInPreviousMonth - weekPositionOfCurrentMonthFirstDay

        return getDaysForMonthAndRemainingNextMonthDays(
            rowStartingDay = firstRowStartDay,
            numberOfDaysInMonth = numberOfDaysInPreviousMonth,
            monthDateTime = previousMonth
        )
    }

    private fun getPositionInRelationToWeek(currentMonth: DateTime): Int {
        val dayOfWeekWithOffset = currentMonth.dayOfWeekInt - MONDAY_START_OF_WEEK_OFFSET
        val indexedDayOfMonth = (DAYS_IN_A_WEEK + dayOfWeekWithOffset) % 7
        return indexedDayOfMonth - MONDAY_START_OF_WEEK_OFFSET
    }

    private fun calculateMiddleRows(
        currentMonth: DateTime,
        firstRow: List<CalendarDay>,
    ): MiddleRows {
        val firstDayOfSecondRow = firstRow.last().day + 1
        val secondRowDayRange = (firstDayOfSecondRow until firstDayOfSecondRow + DAYS_IN_A_WEEK)
        val secondRowDays = createFromDayRange(secondRowDayRange, currentMonth)

        val firstDayOfThirdRow = secondRowDayRange.last() + 1
        val thirdRowDayRange = (firstDayOfThirdRow until firstDayOfThirdRow + DAYS_IN_A_WEEK)
        val thirdRow = createFromDayRange(thirdRowDayRange, currentMonth)

        val firstDayOfFourthRow = thirdRowDayRange.last() + 1
        val fourthRowDays = (firstDayOfFourthRow until firstDayOfFourthRow + DAYS_IN_A_WEEK)
        val fourthRow = createFromDayRange(fourthRowDays, currentMonth)

        val firstDayOfFifthRow = fourthRowDays.last() + 1
        val numberOfDaysInCurrentMonth = currentMonth.getNumberOfDaysInMonth()
        val fifthRow =
            if (firstDayOfFifthRow + DAYS_IN_A_WEEK > numberOfDaysInCurrentMonth) {
                getDaysForMonthAndRemainingNextMonthDays(
                    rowStartingDay = firstDayOfFifthRow,
                    numberOfDaysInMonth = numberOfDaysInCurrentMonth,
                    monthDateTime = currentMonth
                )
            } else {
                val currentMonthDayRange = firstDayOfFifthRow until firstDayOfFifthRow + DAYS_IN_A_WEEK
                createFromDayRange(currentMonthDayRange, currentMonth)
            }

        return MiddleRows(
            second = secondRowDays,
            third = thirdRow,
            fourth = fourthRow,
            fifth = fifthRow
        )
    }

    private fun calculateLastRow(currentMonth: DateTime, fifthRow: List<CalendarDay>): List<CalendarDay> {
        val lastRowStartingDay = fifthRow.last().day + 1
        val nextMonth = currentMonth.plus(MonthSpan(1))
        return if (lastRowStartingDay >= 28) {
            val numberOfDaysInCurrentMonth = currentMonth.getNumberOfDaysInMonth()
            return getDaysForMonthAndRemainingNextMonthDays(
                rowStartingDay = lastRowStartingDay,
                numberOfDaysInMonth = numberOfDaysInCurrentMonth,
                monthDateTime = currentMonth
            )
        } else {
            val nextMonthDayRange = (lastRowStartingDay until lastRowStartingDay + DAYS_IN_A_WEEK)
            createFromDayRange(nextMonthDayRange, nextMonth)
        }
    }

    private fun getDaysForMonthAndRemainingNextMonthDays(
        rowStartingDay: Int,
        numberOfDaysInMonth: Int,
        monthDateTime: DateTime,
    ): List<CalendarDay> {
        val monthDayRange = rowStartingDay..numberOfDaysInMonth
        val monthDays = createFromDayRange(monthDayRange, monthDateTime)

        val nextMonth = monthDateTime.plus(MonthSpan(1))
        val nextMonthRemainingDays = DAYS_IN_A_WEEK - monthDayRange.count()
        val nextMonthDays = createFromDayRange(1..nextMonthRemainingDays, nextMonth)

        return monthDays + nextMonthDays
    }

    private fun DateTime.getNumberOfDaysInMonth(): Int {
        return month.days(year)
    }

    private fun createFromDayRange(
        dayRange: IntRange,
        monthDateTime: DateTime
    ): List<CalendarDay> {
        val monthNumber = monthDateTime.month.index1
        val monthYear = monthDateTime.yearInt
        return dayRange.map { dayNumber ->
            CalendarDay(dayNumber, monthNumber, monthYear)
        }
    }
}
