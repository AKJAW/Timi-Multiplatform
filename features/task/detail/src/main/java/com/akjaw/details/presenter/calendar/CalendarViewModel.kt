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
) : ViewModel() {

    private var currentMonth = DateTime.fromUnix(timestampProvider.getMilliseconds().value)

    private val mutableViewState = MutableStateFlow(
        CalendarViewState(
            monthName = getMonthName(currentMonth),
            dayRows = listOf(
                listOf(28..30, 1..4).toDays(),
                listOf(5..11).toDays(),
                listOf(12..18).toDays(),
                listOf(19..25).toDays(),
                listOf(26..31, 1..6).toDays(),
            )
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
}

internal fun List<IntRange>.toDays(): List<Day> =
    this.map { Day(it.toString()) }
