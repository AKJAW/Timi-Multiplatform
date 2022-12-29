package com.akjaw.timi.kmp.feature.database.entry

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.TimeEntryEntityQueries
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class TimeEntrySqlDelightRepository(
    private val timeEntryEntityQueries: TimeEntryEntityQueries
) : TimeEntryRepository {

    override fun getAll(): Flow<List<TimeEntry>> =
        timeEntryEntityQueries.selectAll(mapper = ::toDomain).asFlow().mapToList()

    override fun getByTaskIds(ids: List<Long>): Flow<List<TimeEntry>> =
        timeEntryEntityQueries.selectByIds(taskId = ids, mapper = ::toDomain).asFlow().mapToList()

    override fun insert(
        id: Long?,
        taskId: Long,
        timeAmount: TimestampMilliseconds,
        date: TimestampMilliseconds
    ) {
        timeEntryEntityQueries.insert(id, taskId, timeAmount, date)
    }

    override fun deleteById(entryId: Long) {
        timeEntryEntityQueries.deleteById(entryId)
    }

    private fun toDomain(
        id: Long,
        taskId: Long,
        timeAmount: TimestampMilliseconds,
        date: TimestampMilliseconds
    ) = TimeEntry(id, taskId, timeAmount, date)
}
