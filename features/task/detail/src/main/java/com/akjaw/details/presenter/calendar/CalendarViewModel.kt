package com.akjaw.details.presenter.calendar

import androidx.lifecycle.ViewModel
import com.akjaw.core.common.composition.BackgroundDispatcherQualifier
import com.akjaw.core.common.domain.TimestampProvider
import com.soywiz.klock.DateTime
import com.soywiz.klock.MonthSpan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    @BackgroundDispatcherQualifier private val backgroundDispatcher: CoroutineDispatcher,
    private val timestampProvider: TimestampProvider,
    private val calendarDaysCalculator: CalendarDaysCalculator
) : ViewModel() {

    private var currentMonth = DateTime.fromUnix(timestampProvider.getMilliseconds().value)

    private val mutableViewState = MutableStateFlow(
        CalendarViewState(
            monthName = getMonthName(currentMonth),
            calendarDayRows = getMonthDays(currentMonth),
            previousMonth = MonthViewState(
                monthName = getMonthName(currentMonth.minus(MonthSpan(1))),
            ),
            nextMonth = MonthViewState(
                monthName = getMonthName(currentMonth.plus(MonthSpan(1))),
            )
        )
    )
    val viewState: StateFlow<CalendarViewState> = mutableViewState

    fun changeToNextMonth() {
        val newCurrentMonth = currentMonth.plus(MonthSpan(1))
        mutableViewState.value = mutableViewState.value.calculateNewState(
            previousMonth = currentMonth,
            currentMonth = newCurrentMonth,
            nextMonth = newCurrentMonth.plus(MonthSpan(1))
        )
        currentMonth = newCurrentMonth
    }

    fun changeToPreviousMonth() {
        val newCurrentMonth = currentMonth.minus(MonthSpan(1))
        mutableViewState.value = mutableViewState.value.calculateNewState(
            previousMonth = newCurrentMonth.minus(MonthSpan(1)),
            currentMonth = newCurrentMonth,
            nextMonth = currentMonth
        )
        currentMonth = newCurrentMonth
    }

    private fun CalendarViewState.calculateNewState(
        previousMonth: DateTime,
        currentMonth: DateTime,
        nextMonth: DateTime
    ): CalendarViewState {
        return copy(
            previousMonth = MonthViewState(monthName = getMonthName(previousMonth)),
            monthName = getMonthName(currentMonth),
            nextMonth = MonthViewState(monthName = getMonthName(nextMonth)),
        )
    }

    private fun getMonthName(dateTime: DateTime): String = dateTime.month.localName

    private fun getMonthDays(currentMonth: DateTime): List<List<CalendarDay>> { // TODO on background thread
        return calendarDaysCalculator.calculate(currentMonth)
    }
}
