package com.akjaw.timi.kmp.feature.task.api.detail.presentation

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.CalendarViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.DayViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.model.TimeEntryUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TaskDetailViewModel {

    val selectedDay: StateFlow<CalendarDay>

    val calendarViewState: StateFlow<CalendarViewState>

    fun selectDay(day: DayViewState)

    fun getTimeEntries(day: CalendarDay): Flow<List<TimeEntryUi>>

    fun addTimeEntry(
        hours: Int,
        minutes: Int,
        day: CalendarDay
    )

    fun removeTimeEntry(id: Long)
}
