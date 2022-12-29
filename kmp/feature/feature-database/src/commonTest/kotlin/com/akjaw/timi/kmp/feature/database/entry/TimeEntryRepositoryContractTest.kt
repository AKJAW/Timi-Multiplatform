package com.akjaw.timi.kmp.feature.database.entry

import app.cash.turbine.test
import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

abstract class TimeEntryRepositoryContractTest {

    abstract var systemUnderTest: TimeEntryRepository

    @Test
    fun `Entry is correctly inserted for an existing task`() = runTest {
        val taskId = 22L
        insertTask(taskId)
        val date = CalendarDay(29, 12, 2022)

        insertEntry(taskId, 60_000, date)

        systemUnderTest.getAll().test {
            assertSoftly(awaitItem()) {
                shouldHaveSize(1)
                val entry = first()
                entry.taskId shouldBe taskId
                entry.timeAmount shouldBe TimestampMilliseconds(60_000)
                entry.date shouldBe date
            }
        }
    }

    @Test
    fun `Entry is correctly removed`() = runTest {
        val taskId = 22L
        insertTask(taskId)
        insertEntry(taskId, entryId = 1)
        insertEntry(taskId, entryId = 2)

        systemUnderTest.deleteById(1)

        systemUnderTest.getAll().test {
            assertSoftly(awaitItem()) {
                shouldHaveSize(1)
                first().id shouldBe 2
            }
        }
    }

    @Test
    fun `Retrieving entries by task ids works correctly`() = runTest {
        val firstTaskId = 11L
        createTaskWithOneEntry(taskId = firstTaskId, entryId = 1)
        val secondTaskId = 22L
        createTaskWithOneEntry(taskId = secondTaskId, entryId = 2)
        val irrelevantTaskId = 55L
        createTaskWithOneEntry(taskId = irrelevantTaskId, entryId = 3)

        systemUnderTest.getByTaskIds(listOf(firstTaskId, secondTaskId)).test {
            assertSoftly(awaitItem()) {
                shouldHaveSize(2)
                get(0).id shouldBe 1
                get(1).id shouldBe 2
            }
        }
    }

    // TODO add test for flow update

    private fun createTaskWithOneEntry(taskId: Long, entryId: Long) {
        insertTask(taskId)
        insertEntry(taskId, entryId = entryId)
    }

    abstract fun insertEntry(
        taskId: Long,
        amount: Long = 0,
        date: CalendarDay = CalendarDay(0),
        entryId: Long? = null
    )

    abstract fun insertTask(taskId: Long)
}
