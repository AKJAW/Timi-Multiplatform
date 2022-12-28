package com.akjaw.timi.kmp.feature.database.adapter

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.TaskEntity
import com.akjaw.timi.kmp.feature.database.TimeEntryEntity
import com.akjaw.timi.kmp.feature.task.api.domain.model.TaskColor
import com.squareup.sqldelight.ColumnAdapter

private class TimestampMillisecondsAdapter : ColumnAdapter<TimestampMilliseconds, Long> {

    override fun decode(databaseValue: Long): TimestampMilliseconds = TimestampMilliseconds(databaseValue)

    override fun encode(value: TimestampMilliseconds): Long = value.value
}

internal val timestampMillisecondsAdapterAdapter =
    TimeEntryEntity.Adapter(TimestampMillisecondsAdapter(), TimestampMillisecondsAdapter())
