package com.akjaw.task.list.data

import com.akjaw.task.TaskEntity
import com.akjaw.task.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import com.akjaw.timi.kmp.feature.task.domain.model.TaskColor
import com.akjaw.task.list.InMemoryTaskEntityQueriesFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

internal class AddTaskToDatabaseTest {

    companion object {
        private val TASK1 = Task(0, "name", backgroundColor = TaskColor(22))
        private val TASK2 = Task(-1, "name2", backgroundColor = TaskColor(2))
    }

    private val inMemoryTaskEntityQueriesFactory = InMemoryTaskEntityQueriesFactory()
    private lateinit var queries: TaskEntityQueries
    private lateinit var systemUnderTest: AddTaskToDatabase

    @BeforeEach
    fun setUp() {
        queries = inMemoryTaskEntityQueriesFactory.create()
        systemUnderTest = AddTaskToDatabase(queries)
    }

    @Test
    fun `Inserting a task correctly adds it to the database`() = runBlockingTest {
        systemUnderTest.execute(TASK1)

        val result = queries.selectAllTasks().executeAsList().firstOrNull()
        expectThat(result).isEqualTo(
            TaskEntity(
                id = 1,
                position = 0,
                name = "name",
                color = 22
            )
        )
    }

    @Test
    fun `The identifier is auto incremented`() = runBlockingTest {
        systemUnderTest.execute(TASK1)

        systemUnderTest.execute(TASK2)

        val result = queries.selectAllTasks().executeAsList()
        val taskWithCorrectId = result.find { it.id == 2L }
        expectThat(taskWithCorrectId).isNotNull()
    }
}
