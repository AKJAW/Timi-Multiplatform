package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.domain.Task
import com.akjaw.task.api.domain.TaskColor
import com.akjaw.task.list.InMemoryTaskEntityQueriesFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

internal class GetTasksFromDatabaseTest {

    private val inMemoryTaskEntityQueriesFactory = InMemoryTaskEntityQueriesFactory()
    private lateinit var queries: TaskEntityQueries
    private lateinit var systemUnderTest: GetTasksFromDatabase

    @BeforeEach
    fun setUp() {
        queries = inMemoryTaskEntityQueriesFactory.create()
        systemUnderTest = GetTasksFromDatabase(queries)
    }

    @Test
    fun `The task is correctly mapped from the database`(): Unit = runBlocking {
        queries.insertTask(id = 1, position = 0, name = "name", color = 22)
        queries.insertTask(id = 2, position = 0, name = "name2", color = 33)

        val result = systemUnderTest.execute().first()

        expect {
            that(result).hasSize(2)
            that(result.first()).isEqualTo(
                Task(
                    id = 1,
                    name = "name",
                    backgroundColor = TaskColor(22),
                    isSelected = false
                )
            )
            that(result.last()).isEqualTo(
                Task(
                    id = 2,
                    name = "name2",
                    backgroundColor = TaskColor(33),
                    isSelected = false
                )
            )
        }
    }
}
