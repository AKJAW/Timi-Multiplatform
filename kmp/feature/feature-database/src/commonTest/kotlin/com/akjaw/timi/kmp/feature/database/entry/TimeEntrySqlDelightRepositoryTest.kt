package com.akjaw.timi.kmp.feature.database.entry

import app.cash.turbine.test
import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.composition.createDatabase
import com.akjaw.timi.kmp.feature.database.test.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TaskColor
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TimeEntrySqlDelightRepositoryTest : TimeEntryRepositoryContractTest() {

    private lateinit var taskEntityQueries: TaskEntityQueries
    override lateinit var systemUnderTest: TimeEntryRepository

    @BeforeTest
    fun setUp() {
        val database = createDatabase(sqlDriver = createTestSqlDriver(), logger = { println(it) })
        taskEntityQueries = database.taskEntityQueries
        systemUnderTest = TimeEntrySqlDelightRepository(database.timeEntryEntityQueries)
    }

    @Test
    fun `Entry cannot be created without a corresponding task`() = runTest {
        shouldThrowAny {
            insertEntry(2)
        }
    }

    @Test
    fun `When a task is removed its entries are also removed`() = runTest {
        val taskId = 22L
        insertTask(taskId)
        insertEntry(taskId)
        insertEntry(taskId)

        taskEntityQueries.deleteTaskById(taskId)

        systemUnderTest.getAll().test {
            awaitItem() shouldBe emptyList()
        }
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
        taskEntityQueries.insertTask(
            id = taskId,
            position = 0,
            name = "",
            color = TaskColor(0f, 0f, 0f)
        )
    }
}
