package com.akjaw.timi.kmp.feature.database.entry

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import kotlinx.coroutines.flow.Flow

interface TimeEntryRepository {

    fun getAll(): Flow<List<TimeEntry>>

    fun getByTaskIds(ids: List<Long>): Flow<List<TimeEntry>>

    fun insert(
        id: Long?,
        taskId: Long,
        timeAmount: TimestampMilliseconds,
        date: TimestampMilliseconds
    )

    fun deleteById(entryId: Long)
}
