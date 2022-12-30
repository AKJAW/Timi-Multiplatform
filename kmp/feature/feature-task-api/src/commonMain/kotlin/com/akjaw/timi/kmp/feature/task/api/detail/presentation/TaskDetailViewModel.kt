package com.akjaw.timi.kmp.feature.task.api.detail.presentation

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.model.TimeEntryUi
import kotlinx.coroutines.flow.Flow

interface TaskDetailViewModel {

    // TODO should be a list of CalendarDays and returns map? - Only for bottom sheet
    fun getTimeEntries(day: CalendarDay): Flow<List<TimeEntryUi>>

    fun addTimeEntry(
        timeAmount: TimestampMilliseconds, // TODO change to hours and seconds
        day: CalendarDay
    )

    fun removeTimeEntry(id: Long)
}
