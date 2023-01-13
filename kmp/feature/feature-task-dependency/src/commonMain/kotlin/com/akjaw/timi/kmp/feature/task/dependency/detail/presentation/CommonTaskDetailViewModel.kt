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
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.DayViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.toCalendarDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update

// TODO should have list in the name?
internal class CommonTaskDetailViewModel(
    private val taskId: Long,
    private val timeEntryRepository: TimeEntryRepository,
    private val calendarViewModel: CalendarViewModel, // TODO rename?
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
) : TaskDetailViewModel {

    private val allTimeEntries: Flow<List<TimeEntryUi>> = getEntries()

    // TODO initial value should be always today
    override val selectedDay: MutableStateFlow<CalendarDay> = MutableStateFlow(CalendarDay(13, 1, 2023))

    override val calendarViewState: StateFlow<CalendarViewState> = calendarViewModel.viewState

    override val timeEntries: Flow<List<TimeEntryUi>> = combine(
        allTimeEntries,
        selectedDay
    ) { entries, selectedDay ->
        entries.filter { it.date == selectedDay }
    }

    override fun selectDay(day: DayViewState) {
        selectedDay.update { day.toCalendarDay() }
    }

    override fun addTimeEntry(hours: Int, minutes: Int, day: CalendarDay) {
        val totalMinutes = 60 * hours + minutes
        val totalMilliseconds = totalMinutes * 60 * 1000L
        timeEntryRepository.insert(taskId, TimestampMilliseconds(totalMilliseconds), day)
    }

    override fun removeTimeEntry(id: Long) {
        timeEntryRepository.deleteById(id)
    }

    private fun getEntries() = timeEntryRepository.getByTaskIds(listOf(taskId)).map { entries: List<TimeEntry> ->
        entries
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
