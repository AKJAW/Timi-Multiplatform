package com.akjaw.task.list.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.akjaw.task.TaskEntity
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.domain.Task
import com.akjaw.task.list.InMemoryTaskEntityQueriesFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class AddTaskToDatabaseTest {

    companion object {
        private val TASK1 = Task(1, "name", Color.White)
        private val TASK2 = Task(-1, "name2", Color.Black)
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

        val result = queries.selectTaskById(1).executeAsOne()
        expectThat(result).isEqualTo(
            TaskEntity(
                id = 1,
                position = 0,
                name = "name",
                color = Color.White.toArgb()
            )
        )
    }

    @Test
    fun `After inserting the task has the correct id`() = runBlockingTest {
        systemUnderTest.execute(TASK1)

        systemUnderTest.execute(TASK2)

        val result = queries.selectTaskById(2).executeAsOne()
        expectThat(result.name).isEqualTo("name2")
    }
}
