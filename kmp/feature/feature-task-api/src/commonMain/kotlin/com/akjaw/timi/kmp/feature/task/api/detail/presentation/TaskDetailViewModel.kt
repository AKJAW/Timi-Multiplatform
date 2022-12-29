package com.akjaw.timi.kmp.feature.task.api.detail.presentation

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import kotlinx.coroutines.flow.StateFlow

interface TaskDetailViewModel {

    fun getTimeEntries(day: CalendarDay): StateFlow<List<TimeEntry>>

    fun addTimeEntry(
        timeAmount: TimestampMilliseconds,
        day: CalendarDay
    )

    fun removeTimeEntry(id: Long)
}
