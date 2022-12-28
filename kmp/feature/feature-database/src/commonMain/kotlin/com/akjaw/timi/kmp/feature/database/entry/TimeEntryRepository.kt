package com.akjaw.timi.kmp.feature.database.entry

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.task.api.domain.model.TimeEntry
import kotlinx.coroutines.flow.Flow

// TODO create domain TimeEntry model, where to put it?? - Probably task-api, since they have a 1 to 1 task relation
//  AND stopwatch is just for time counting
interface TimeEntryRepository {

    fun getAll(): Flow<List<TimeEntry>>

    fun getByTaskIds(ids: List<Long>): Flow<List<TimeEntry>>

    fun insert(
        id: Long?,
        taskId: Long,
        timeAmount: TimestampMilliseconds,
        date: TimestampMilliseconds,
    )

    fun deleteById(entryId: Long)
}
