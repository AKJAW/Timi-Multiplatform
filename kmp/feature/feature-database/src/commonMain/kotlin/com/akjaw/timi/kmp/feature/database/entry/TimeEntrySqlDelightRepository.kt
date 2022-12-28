package com.akjaw.timi.kmp.feature.database.entry

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.TimeEntryEntity
import com.akjaw.timi.kmp.feature.database.TimeEntryEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class TimeEntrySqlDelightRepository(
    private val timeEntryEntityQueries: TimeEntryEntityQueries,
) : TimeEntryRepository {

    override fun getAll(): Flow<List<TimeEntryEntity>> = timeEntryEntityQueries.selectAll().asFlow().mapToList()

    override fun getByTaskIds(ids: List<Long>): Flow<List<TimeEntryEntity>> =
        timeEntryEntityQueries.selectByIds(ids).asFlow().mapToList()

    override fun insert(
        id: Long?,
        taskId: Long,
        timeAmount: TimestampMilliseconds,
        date: TimestampMilliseconds,
    ) {
        timeEntryEntityQueries.insert(id, taskId, timeAmount, date)
    }

    override fun deleteById(entryId: Long) {
        timeEntryEntityQueries.deleteById(entryId)
    }
}
