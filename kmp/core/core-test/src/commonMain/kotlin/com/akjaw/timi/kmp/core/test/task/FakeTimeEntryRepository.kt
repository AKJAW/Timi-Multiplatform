package com.akjaw.timi.kmp.core.test.task

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.entry.TimeEntryRepository
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

// TODO move to database module so the tests are cohesive?
class FakeTimeEntryRepository : TimeEntryRepository {

    private val taskIdWithEntry: MutableStateFlow<Map<Long, List<TimeEntry>>> = MutableStateFlow(emptyMap())

    fun setEntry(taskId: Long, entries: List<TimeEntry>) {
        this.taskIdWithEntry.updateWithEntry(taskId, entries)
    }

    fun getEntry(taskId: Long): List<TimeEntry>? =
        taskIdWithEntry.value[taskId]

    override fun getAll(): Flow<List<TimeEntry>> {
        return taskIdWithEntry.map { it.values.flatten() }
    }

    override fun getByTaskIds(ids: List<Long>): Flow<List<TimeEntry>> {
        return taskIdWithEntry.map { value: Map<Long, List<TimeEntry>> ->
            value
                .filterKeys { taskId -> ids.contains(taskId) }
                .values
                .flatten()
        }
    }

    private var entryId = 1L
    override fun insert(id: Long?, taskId: Long, timeAmount: TimestampMilliseconds, date: CalendarDay) {
        val currentEntries = taskIdWithEntry.value[taskId] ?: emptyList()
        val newEntries = currentEntries + TimeEntry(id ?: entryId++, taskId, timeAmount, date)
        taskIdWithEntry.updateWithEntry(taskId, newEntries)
    }

    override fun deleteById(entryId: Long) {
        taskIdWithEntry.update { currentEntries ->
            buildMap {
                val newEntries = currentEntries.mapValues { (_, values) ->
                    values.filterNot { it.id == entryId }
                }
                putAll(newEntries)
            }
        }
    }

    private fun MutableStateFlow<Map<Long, List<TimeEntry>>>.updateWithEntry(
        taskId: Long,
        entries: List<TimeEntry>
    ) {
        update { currentEntries ->
            buildMap {
                putAll(currentEntries)
                put(taskId, entries)
            }
        }
    }
}
