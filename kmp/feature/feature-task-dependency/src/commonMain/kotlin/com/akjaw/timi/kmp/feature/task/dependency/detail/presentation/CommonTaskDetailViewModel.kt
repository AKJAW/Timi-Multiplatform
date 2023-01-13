package com.akjaw.timi.kmp.feature.task.dependency.detail.presentation

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.date.format
import com.akjaw.timi.kmp.core.shared.time.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.entry.TimeEntryRepository
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.TaskDetailViewModel
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.model.TimeEntryUi
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.CalendarViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

// TODO should have list in the name?
internal class CommonTaskDetailViewModel(
    private val taskId: Long,
    private val timeEntryRepository: TimeEntryRepository,
    private val calendarViewModel: CalendarViewModel, // TODO rename?
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
) : TaskDetailViewModel {
    // TODO move logic to the domain?

    override val calendarViewState: StateFlow<CalendarViewState> = calendarViewModel.viewState

    override fun getTimeEntries(day: CalendarDay): Flow<List<TimeEntryUi>> {
        // TODO could be moved to a property
        return timeEntryRepository.getByTaskIds(listOf(taskId)).map { entries: List<TimeEntry> ->
            entries
                .filter { it.date == day }
                .map { entry ->
                    TimeEntryUi(
                        id = entry.id,
                        date = entry.date,
                        formattedTime = timestampMillisecondsFormatter.formatWithoutMilliseconds(entry.timeAmount),
                        formattedDate = entry.date.format()
                    )
                }
        }
    }

    override fun addTimeEntry(hours: Int, minutes: Int, day: CalendarDay) {
        val totalMinutes = 60 * hours + minutes
        val totalMilliseconds = totalMinutes * 60 * 1000L
        timeEntryRepository.insert(taskId, TimestampMilliseconds(totalMilliseconds), day)
    }

    override fun removeTimeEntry(id: Long) {
        timeEntryRepository.deleteById(id)
    }
}
