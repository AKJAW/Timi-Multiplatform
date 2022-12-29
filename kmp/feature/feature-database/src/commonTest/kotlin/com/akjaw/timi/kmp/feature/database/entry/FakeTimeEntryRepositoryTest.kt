package com.akjaw.timi.kmp.feature.database.entry

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.test.task.FakeTimeEntryRepository
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
        date: CalendarDay
    ) {
        systemUnderTest.insert(
            taskId = taskId,
            timeAmount = TimestampMilliseconds(amount),
            date = date
        )
    }

    override fun insertTask(taskId: Long) {
        /* Not needed */
    }
}
