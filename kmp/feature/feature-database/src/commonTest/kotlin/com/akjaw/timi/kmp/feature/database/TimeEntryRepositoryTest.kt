package com.akjaw.timi.kmp.feature.database

import app.cash.turbine.test
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.composition.createDatabase
import com.akjaw.timi.kmp.feature.database.entry.TimeEntrySqlDelightRepository
import com.akjaw.timi.kmp.feature.database.test.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TaskColor
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TimeEntryRepositoryTest {

    private lateinit var taskEntityQueries: TaskEntityQueries
    private lateinit var systemUnderTest: TimeEntrySqlDelightRepository

    @BeforeTest
    fun setUp() {
        val database = createDatabase(createTestSqlDriver())
        taskEntityQueries = database.taskEntityQueries
        systemUnderTest = TimeEntrySqlDelightRepository(database.timeEntryEntityQueries)
    }

    @Test
    fun `Entry cannot be created without a corresponding task`() = runTest {
        shouldThrowAny {
            insertEntry(2, 0, 0)
        }
    }

    @Test
    fun `Entry is correctly inserted for an existing task`() = runTest {
        val taskId = 22L
        insertTask(taskId)

        insertEntry(taskId, 60_000, 1672220579147)

        systemUnderTest.getAll().test {
            assertSoftly(awaitItem()) {
                shouldHaveSize(1)
                val entry = first()
                entry.taskId shouldBe taskId
                entry.timeAmount shouldBe TimestampMilliseconds(60_000)
                entry.date shouldBe TimestampMilliseconds(1672220579147)
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
    fun `When a task is removed its entries are also removed`() = runTest {
        val taskId = 22L
        insertTask(taskId)
        insertEntry(taskId, entryId = 1)
        insertEntry(taskId, entryId = 2)

        taskEntityQueries.deleteTaskById(taskId)

        systemUnderTest.getAll().test {
            awaitItem() shouldBe emptyList()
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

    private fun createTaskWithOneEntry(taskId: Long, entryId: Long) {
        insertTask(taskId)
        insertEntry(taskId, entryId = entryId)
    }

    private fun insertEntry(taskId: Long, amount: Long = 0, dateTimestamp: Long = 0, entryId: Long? = null) {
        systemUnderTest.insert(
            id = entryId,
            taskId = taskId,
            timeAmount = TimestampMilliseconds(amount),
            date = TimestampMilliseconds(dateTimestamp)
        )
    }

    private fun insertTask(taskId: Long) {
        taskEntityQueries.insertTask(
            id = taskId,
            position = 0,
            name = "",
            color = TaskColor(0f, 0f, 0f)
        )
    }
}
