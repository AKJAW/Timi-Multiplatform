package com.akjaw.timi.kmp.feature.database.adapter

import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.squareup.sqldelight.ColumnAdapter

internal class TimestampMillisecondsAdapter : ColumnAdapter<TimestampMilliseconds, Long> {

    override fun decode(databaseValue: Long): TimestampMilliseconds = TimestampMilliseconds(databaseValue)

    override fun encode(value: TimestampMilliseconds): Long = value.value
}
