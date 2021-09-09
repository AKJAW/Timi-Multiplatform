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
internal class CalendarViewModel @Inject constructor(
    @BackgroundDispatcherQualifier private val backgroundDispatcher: CoroutineDispatcher,
    private val timestampProvider: TimestampProvider,
    private val calendarDaysCalculator: CalendarDaysCalculator
) : ViewModel() {

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

    private fun getMonthDays(currentMonth: DateTime): List<List<CalendarDay>> { // TODO on background thread
        return calendarDaysCalculator.calculate(currentMonth)
    }
}
