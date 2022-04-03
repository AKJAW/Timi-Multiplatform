package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.InMemoryTaskEntityQueriesFactory
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.api.model.TaskColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
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
        queries.insertTask(id = 1, position = 0, name = "name", color = TaskColor(22f, 22f, 22f))
        queries.insertTask(id = 2, position = 0, name = "name2", color = TaskColor(33f, 33f, 33f))

        val result = systemUnderTest.execute().first()

        expect {
            that(result).hasSize(2)
            that(result.first()).isEqualTo(
                Task(
                    id = 1,
                    name = "name",
                    backgroundColor = TaskColor(22f, 22f, 22f),
                    isSelected = false
                )
            )
            that(result.last()).isEqualTo(
                Task(
                    id = 2,
                    name = "name2",
                    backgroundColor = TaskColor(33f, 33f, 33f),
                    isSelected = false
                )
            )
        }
    }
}
