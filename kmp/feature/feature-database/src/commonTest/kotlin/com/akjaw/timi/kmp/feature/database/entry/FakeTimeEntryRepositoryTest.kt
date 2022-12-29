package com.akjaw.timi.kmp.feature.database.entry

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.test.task.FakeTimeEntryRepository
import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.composition.createDatabase
import com.akjaw.timi.kmp.feature.database.test.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TaskColor
import kotlin.test.BeforeTest

class FakeTimeEntryRepositoryTest : TimeEntryRepositoryContractTest() {

    override lateinit var systemUnderTest: TimeEntryRepository

    @BeforeTest
    fun setUp() {
        systemUnderTest = FakeTimeEntryRepository()
    }

    override fun insertEntry(
        taskId: Long,
        amount: Long,
        date: CalendarDay,
        entryId: Long?
    ) {
        systemUnderTest.insert(
            id = entryId,
            taskId = taskId,
            timeAmount = TimestampMilliseconds(amount),
            date = date
        )
    }

    override fun insertTask(taskId: Long) {
        /* Not needed */
    }
}
