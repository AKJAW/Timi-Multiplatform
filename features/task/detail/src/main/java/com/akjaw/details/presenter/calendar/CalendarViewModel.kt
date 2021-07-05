package com.akjaw.details.presenter.calendar

import androidx.lifecycle.ViewModel
import com.akjaw.core.common.composition.BackgroundDispatcherQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class CalendarViewModel @Inject constructor(
    @BackgroundDispatcherQualifier private val backgroundDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val mutableCurrentMonth = MutableStateFlow(
        CalendarViewState(
            monthName = "July",
            dayRows = listOf(
                listOf(28..30, 1..4).toDays(),
                listOf(5..11).toDays(),
                listOf(12..18).toDays(),
                listOf(19..25).toDays(),
                listOf(26..31, 1..6).toDays(),
            )
        )
    )
    val currentMonth: StateFlow<CalendarViewState> = mutableCurrentMonth
}

internal fun List<IntRange>.toDays(): List<Day> =
    this.map { Day(it.toString()) }
