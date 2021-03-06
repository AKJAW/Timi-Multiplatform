package com.akjaw.task.list.data

import com.akjaw.task.TaskEntity
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.domain.Task
import com.akjaw.task.list.InMemoryTaskEntityQueriesFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

internal class DeleteTasksFromDatabaseTest {

    companion object {
        private val TASK1 = Task(1, "name")
        private val TASK2 = Task(2, "name2")
    }

    private val inMemoryTaskEntityQueriesFactory = InMemoryTaskEntityQueriesFactory()
    private lateinit var queries: TaskEntityQueries
    private lateinit var systemUnderTest: DeleteTasksFromDatabase

    @BeforeEach
    fun setUp() {
        queries = inMemoryTaskEntityQueriesFactory.create()
        systemUnderTest = DeleteTasksFromDatabase(queries)
    }

    @Test
    fun `Deleting a task removes it from the database`() = runBlockingTest {
        givenTasksExists(TASK1, TASK2)

        systemUnderTest.execute(listOf(TASK2))

        val result = queries.selectAllTasks().executeAsList()
        expect {
            that(result).hasSize(1)
            val task = result.first()
            that(task).get(TaskEntity::id).isEqualTo(1)
            that(task).get(TaskEntity::name).isEqualTo("name")
        }
    }

    private fun givenTasksExists(vararg tasks: Task) {
        tasks.forEach { task ->
            queries.insertTask(task.id, 0, task.name, 0)
        }
    }
}
