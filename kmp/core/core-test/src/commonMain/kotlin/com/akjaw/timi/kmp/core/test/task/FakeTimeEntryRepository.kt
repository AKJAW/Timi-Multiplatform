package com.akjaw.timi.kmp.core.test.task

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.entry.TimeEntryRepository
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

// TODO write contract test???
// TODO refactor to flow?
class FakeTimeEntryRepository : TimeEntryRepository {

    private val entries: MutableMap<Long, List<TimeEntry>> = mutableMapOf()

    fun setEntry(taskId: Long, entries: List<TimeEntry>) {
        this.entries[taskId] = entries
    }

    fun getEntry(taskId: Long): List<TimeEntry>? =
        entries[taskId]

    override fun getAll(): Flow<List<TimeEntry>> {
        TODO("Not yet implemented")
    }

    override fun getByTaskIds(ids: List<Long>): Flow<List<TimeEntry>> {
        val tasksEntries: Map<Long, List<TimeEntry>> = entries.filterKeys { taskId -> ids.contains(taskId) }
        return MutableStateFlow(tasksEntries.values.flatten())
    }

    override fun insert(id: Long?, taskId: Long, timeAmount: TimestampMilliseconds, date: CalendarDay) {
        val existingEntries: List<TimeEntry> = entries[taskId] ?: emptyList()
        entries[taskId] = existingEntries + TimeEntry(id ?: 0, taskId, timeAmount, date)
    }

    override fun deleteById(entryId: Long) {
        val newEntries = entries.mapValues { (_, values) ->
            values.filterNot { it.id == entryId }
        }
        entries.clear()
        entries.putAll(newEntries)
    }
}
