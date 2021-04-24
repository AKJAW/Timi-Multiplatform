package com.akjaw.task.list

import com.akjaw.task.TaskEntity
import com.akjaw.task.TaskEntityQueries
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class TaskDatabaseTest {

    companion object {
        private val TASK1 = TaskEntity(1, 0, "name", 123)
        private val TASK2 = TaskEntity(-1, 0, "name2", 123)
    }

    private val inMemoryTaskEntityQueriesFactory = InMemoryTaskEntityQueriesFactory()
    private lateinit var queries: TaskEntityQueries

    @BeforeEach
    fun setUp() {
        queries = inMemoryTaskEntityQueriesFactory.create()
    }

    @Test
    fun `Inserting a task is reflected in the list`() {
        givenTaskExists(TASK1)

        val result = queries.selectAllTasks().executeAsList()
        expectThat(result).isEqualTo(listOf(TASK1))
    }

    @Test
    fun `Inserting a task without an id sets the incremented id as a number`() {
        givenTaskExists(TASK1)

        givenTaskExists(TASK2, isIdNull = true)

        val result = queries.selectAllTasks().executeAsList()
        val taskEntity = result.last()
        expectThat(taskEntity.id).isEqualTo(2)
    }

    @Test
    fun `Selecting task by id works as expected`() {
        givenTaskExists(TASK1)

        val result = queries.selectTaskById(1).executeAsOne()

        expectThat(result).isEqualTo(TASK1)
    }

    @Test
    fun `Updating a task works as expected`() {
        givenTaskExists(TASK1)

        queries.updateTaskName(
            position = 1,
            name = "different",
            color = TASK1.color,
            TASK1.id
        )

        val result = queries.selectAllTasks().executeAsList().first()
        expectThat(result).isEqualTo(TASK1.copy(position = 1, name = "different"))
    }

    @Test
    fun `Deleting a task works as expected`() {
        givenTaskExists(TASK1)

        queries.deleteTaskById(TASK1.id)

        val result = queries.selectAllTasks().executeAsList()
        expectThat(result).isEmpty()
    }

    private fun givenTaskExists(task: TaskEntity, isIdNull: Boolean = false) {
        val id = if (isIdNull) null else task.id
        queries.insertTask(id, task.position, task.name, task.color)
    }
}
