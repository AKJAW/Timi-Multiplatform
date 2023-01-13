package com.akjaw.timi.kmp.feature.task.dependency

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.test.time.StubTimestampProvider
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel

internal fun createCalendarViewModel(
    timestampProvider: TimestampProvider = StubTimestampProvider()
) = CalendarViewModel(
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)
