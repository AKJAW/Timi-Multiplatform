package com.akjaw.details.presenter.calendar

import androidx.lifecycle.ViewModel
import com.akjaw.core.common.composition.BackgroundDispatcherQualifier
import com.akjaw.core.common.domain.TimestampProvider
import com.soywiz.klock.DateTime
import com.soywiz.klock.MonthSpan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class CalendarViewModel @Inject constructor(
    @BackgroundDispatcherQualifier private val backgroundDispatcher: CoroutineDispatcher,
    private val timestampProvider: TimestampProvider,
    private val calendarDaysCalculator: CalendarDaysCalculator
) : ViewModel() {

    private var currentMonth = DateTime.fromUnix(timestampProvider.getMilliseconds().value)

    private val mutableViewState = MutableStateFlow(
        CalendarViewState(
            monthName = getMonthName(currentMonth),
            calendarDayRows = getMonthDays(currentMonth)
        )
    )
    val viewState: StateFlow<CalendarViewState> = mutableViewState

    fun changeToNextMonth() {
        currentMonth = currentMonth.plus(MonthSpan(1))
        mutableViewState.value = mutableViewState.value.copy(monthName = getMonthName(currentMonth))
    }

    fun changeToPreviousMonth() {
        currentMonth = currentMonth.minus(MonthSpan(1))
        mutableViewState.value = mutableViewState.value.copy(monthName = getMonthName(currentMonth))
    }

    private fun getMonthName(dateTime: DateTime): String = dateTime.month.localName

    private fun getMonthDays(currentMonth: DateTime): List<List<CalendarDay>> { // TODO on background thread
        return calendarDaysCalculator.calculate(currentMonth)
    }
}
