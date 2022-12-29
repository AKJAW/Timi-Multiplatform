package com.akjaw.timi.kmp.feature.task.api.list.domain.model

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds

data class TimeEntry(
    val id: Long,
    val taskId: Long,
    val timeAmount: TimestampMilliseconds,
    val date: CalendarDay
)
