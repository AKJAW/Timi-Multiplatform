package com.akjaw.timi.kmp.feature.task.dependency.detail.presentation

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.entry.TimeEntryRepository
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.TaskDetailViewModel
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TODO should have list in the name?
internal class CommonTaskDetailViewModel(
    private val task: Task,
    private val timeEntryRepository: TimeEntryRepository
) : TaskDetailViewModel {
    // TODO move logic to the domain?

    override fun getTimeEntries(day: CalendarDay): Flow<List<TimeEntry>> {
        // TODO could be moved to a property
        return timeEntryRepository.getByTaskIds(listOf(task.id)).map { entries: List<TimeEntry> ->
            entries.filter { it.date == day }
        }
    }

    override fun addTimeEntry(timeAmount: TimestampMilliseconds, day: CalendarDay) {
        timeEntryRepository.insert(null, task.id, timeAmount, day)
    }

    override fun removeTimeEntry(id: Long) {
        timeEntryRepository.deleteById(id)
    }
}
