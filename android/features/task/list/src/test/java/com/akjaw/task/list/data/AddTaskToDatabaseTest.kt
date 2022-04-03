package com.akjaw.task.list.data

import com.akjaw.task.list.InMemoryTaskEntityQueriesFactory
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.api.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntity
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.AddTaskToDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull


@OptIn(ExperimentalCoroutinesApi::class)
internal class AddTaskToDatabaseTest {

    companion object {
        private val TASK1 = Task(0, "name", backgroundColor = TaskColor(22f, 22f, 22f))
        private val TASK2 = Task(-1, "name2", backgroundColor = TaskColor(0f, 0f, 0f))
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
    fun `Inserting a task correctly adds it to the database`() = runTest {
        systemUnderTest.execute(TASK1)

        val result = queries.selectAllTasks().executeAsList().firstOrNull()
        expectThat(result).isEqualTo(
            TaskEntity(
                id = 1,
                position = 0,
                name = "name",
                color = TaskColor(22f, 22f, 22f)
            )
        )
    }

    @Test
    fun `The identifier is auto incremented`() = runTest {
        systemUnderTest.execute(TASK1)

        systemUnderTest.execute(TASK2)

        val result = queries.selectAllTasks().executeAsList()
        val taskWithCorrectId = result.find { it.id == 2L }
        expectThat(taskWithCorrectId).isNotNull()
    }
}
