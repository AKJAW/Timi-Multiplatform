package com.akjaw.timi.kmp.feature.task.dependency.domain

import com.akjaw.timi.kmp.feature.task.api.domain.AddTask
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.domain.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.composition.databaseModule
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntity
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.database.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.dependency.list.composition.taskListModule
import com.squareup.sqldelight.db.SqlDriver
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class AddTaskToDatabaseTest : KoinComponent {

    companion object {
        private val TASK1 = Task(0, "name", backgroundColor = TaskColor(22f, 22f, 22f))
        private val TASK2 = Task(-1, "name2", backgroundColor = TaskColor(0f, 0f, 0f))
    }

    private val taskEntityQueries: TaskEntityQueries by inject()
    private val systemUnderTest: AddTask by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                databaseModule,
                taskListModule,
                module {
                    single<SqlDriver> { createTestSqlDriver() }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Inserting a task correctly adds it to the database`() = runTest {
        systemUnderTest.execute(TASK1)

        val result = taskEntityQueries.selectAllTasks().executeAsList().firstOrNull()
        result shouldBe TaskEntity(
            id = 1,
            position = 0,
            name = "name",
            color = TaskColor(22f, 22f, 22f)
        )
    }

    @Test
    fun `The identifier is auto incremented`() = runTest {
        systemUnderTest.execute(TASK1)

        systemUnderTest.execute(TASK2)

        val result = taskEntityQueries.selectAllTasks().executeAsList()
        val taskWithCorrectId = result.find { it.id == 2L }
        taskWithCorrectId.shouldNotBeNull()
    }
}
