package com.akjaw.timi.kmp.feature.task.dependency.list.domain

import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.composition.databaseModule
import com.akjaw.timi.kmp.feature.database.test.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.api.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.domain.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.list.composition.taskListModule
import com.squareup.sqldelight.db.SqlDriver
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
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
internal class DeleteTasksFromDatabaseTest : KoinComponent {

    companion object {
        private val TASK1 = Task(1, "name")
        private val TASK2 = Task(2, "name2")
    }

    private val taskEntityQueries: TaskEntityQueries by inject()
    private val systemUnderTest: DeleteTasks by inject()

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
    fun `Deleting a task removes it from the database`() = runTest {
        givenTasksExists(TASK1, TASK2)

        systemUnderTest.execute(listOf(TASK2))

        val result = taskEntityQueries.selectAllTasks().executeAsList()
        assertSoftly {
            result shouldHaveSize 1
            val task = result.first()
            task.id shouldBe 1
            task.name shouldBe "name"
        }
    }

    private fun givenTasksExists(vararg tasks: Task) {
        tasks.forEach { task ->
            taskEntityQueries.insertTask(task.id, 0, task.name, TaskColor(0f, 0f, 0f))
        }
    }
}
