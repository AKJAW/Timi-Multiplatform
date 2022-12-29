package com.akjaw.timi.kmp.feature.database.adapter

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.TimeEntryEntity
import com.squareup.sqldelight.ColumnAdapter

internal class CalendarDayAdapter : ColumnAdapter<CalendarDay, String> {

    companion object {
        private const val SEPARATOR = "|"
    }

    override fun decode(databaseValue: String): CalendarDay {
        val (day, month, year) = databaseValue.split(SEPARATOR)
        return CalendarDay(day.toInt(), month.toInt(), year.toInt())
    }

    override fun encode(value: CalendarDay): String =
        listOf(value.day, value.month, value.year).joinToString(SEPARATOR)
}
