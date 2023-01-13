package com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.CalendarViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.DayViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.MonthViewState
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.soywiz.klock.DateTime
import com.soywiz.klock.MonthSpan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// TODO use inside detail VM and rename?
class CalendarViewModel(
    private val timestampProvider: TimestampProvider,
    private val calendarDaysCalculator: CalendarDaysCalculator
) {

    companion object {
        const val NUMBER_OF_MONTHS = 50
        const val CURRENT_MONTH_INDEX = 40
    }

    private var currentMonth = DateTime.fromUnix(timestampProvider.getMilliseconds().value)

    private val mutableViewState = MutableStateFlow(
        CalendarViewState(
            months = createMonthList()
        )
    )
    val viewState: StateFlow<CalendarViewState> = mutableViewState
    private val initialDays = mutableViewState.value.months

    private fun createMonthList(): List<MonthViewState> {
        val mutableList = mutableListOf<MonthViewState>()

        (0 until NUMBER_OF_MONTHS).forEach { index ->
            val monthOffset = CURRENT_MONTH_INDEX * -1 + index
            val month = currentMonth + MonthSpan(monthOffset)
            mutableList.add(
                MonthViewState(
                    monthName = getMonthName(month),
                    calendarDayRows = getMonthDays(month)
                )
            )
        }

        return mutableList
    }

    private fun getMonthName(dateTime: DateTime): String {
        val currentYear = currentMonth.year
        return if (dateTime.year != currentYear) {
            "${dateTime.month.localName} ${dateTime.year.year}"
        } else {
            dateTime.month.localName
        }
    }

    private fun getMonthDays(month: DateTime): List<List<DayViewState>> { // TODO on background thread
        return calendarDaysCalculator
            .calculate(month)
            .map { row ->
                row.map { calendarDay ->
                    DayViewState(
                        calendarDay.day,
                        calendarDay.month,
                        calendarDay.year
                    )
                }
            }
    }

    fun selectDay(dayViewStateToSelect: DayViewState) {
        val currentValue = mutableViewState.value
        val selectedMonthIndex = currentValue.getMonthIndexForSelectedDay(dayViewStateToSelect)
        val monthContainingSelectedDay = currentValue.months[selectedMonthIndex]
        val newMonth = monthContainingSelectedDay
            .createMonthListWithSelectedDay(dayViewStateToSelect)

        val mutableInitialDays = initialDays.toMutableList()
        mutableInitialDays[selectedMonthIndex] =
            mutableInitialDays[selectedMonthIndex].copy(calendarDayRows = newMonth)

        mutableViewState.value = currentValue.copy(months = mutableInitialDays)
    }

    private fun MonthViewState.createMonthListWithSelectedDay(
        dayViewStateToSelect: DayViewState
    ): List<List<DayViewState>> = calendarDayRows.map { row ->
        row.map { day ->
            if (areDatesTheSame(day, dayViewStateToSelect)) {
                day.copy(isSelected = day.isSelected.not())
            } else {
                day.copy(isSelected = false)
            }
        }
    }

    private fun CalendarViewState.getMonthIndexForSelectedDay(
        dayViewStateToSelect: DayViewState
    ): Int = months.indexOfFirst { viewState ->
        val middleRow = viewState.calendarDayRows[2]
        val dayOfCurrentMonth = middleRow.first()
        dayOfCurrentMonth.month == dayViewStateToSelect.month &&
            dayOfCurrentMonth.year == dayViewStateToSelect.year
    }

    private fun areDatesTheSame(
        day: DayViewState,
        dayViewStateToSelect: DayViewState
    ): Boolean = day.day == dayViewStateToSelect.day &&
        day.month == dayViewStateToSelect.month &&
        day.year == dayViewStateToSelect.year
}
