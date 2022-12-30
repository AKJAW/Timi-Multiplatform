package com.akjaw.timi.kmp.feature.task.api.detail.presentation.model

import com.akjaw.timi.kmp.core.shared.date.CalendarDay

data class TimeEntryUi(
    val id: Long,
    val date: CalendarDay,
    val formattedTime: String,
    val formattedDate: String,
)
