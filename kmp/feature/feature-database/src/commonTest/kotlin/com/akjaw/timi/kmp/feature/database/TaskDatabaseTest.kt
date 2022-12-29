package com.akjaw.timi.kmp.feature.database

import com.akjaw.timi.kmp.feature.database.composition.databaseModule
import com.akjaw.timi.kmp.feature.database.test.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TaskColor
import com.squareup.sqldelight.db.SqlDriver
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

// TODO refactor to not use Koin?
class TaskDatabaseTest : KoinComponent {

    companion object {
        private val TASK1 = TaskEntity(
            id = 1,
            position = 0,
            name = "name",
            color = TaskColor(
                red = 123 / 255f,
                green = 123 / 255f,
                blue = 123 / 255f
            )
        )
        private val TASK2 =
            TaskEntity(
                id = -1,
                position = 0,
                name = "name2",
                color = TaskColor(
                    red = 123 / 255f,
                    green = 123 / 255f,
                    blue = 123 / 255f
                )
            )
    }

    private val taskEntityQueries: TaskEntityQueries by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                databaseModule,
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
    fun `Inserting a task is reflected in the list`() {
        givenTaskExists(TASK1)

        val result = taskEntityQueries.selectAllTasks().executeAsList()

        result shouldBe listOf(TASK1)
    }

    @Test
    fun `Inserting a task without an id sets the incremented previous id as the id`() {
        givenTaskExists(TASK1.copy(id = 2))

        givenTaskExists(TASK2, isIdNull = true)

        val result = taskEntityQueries.selectAllTasks().executeAsList()
        val taskEntity = result.last()
        taskEntity.id shouldBe 3
    }

    @Test
    fun `Selecting task by id works as expected`() {
        try {
            givenTaskExists(TASK1)

            val result = taskEntityQueries.selectTaskById(1).executeAsOne()

            result shouldBe TASK1
        } catch (e: Exception) {
            println(e)
            e
        }
    }

    @Test
    fun `Updating a task works as expected`() {
        givenTaskExists(TASK1)

        taskEntityQueries.updateTaskName(
            position = 1,
            name = "different",
            color = TASK1.color,
            TASK1.id
        )

        val result = taskEntityQueries.selectAllTasks().executeAsList().first()
        result shouldBe TASK1.copy(position = 1, name = "different")
    }

    @Test
    fun `Deleting a task works as expected`() {
        givenTaskExists(TASK1)

        taskEntityQueries.deleteTaskById(TASK1.id)

        val result = taskEntityQueries.selectAllTasks().executeAsList()
        result.shouldBeEmpty()
    }

    private fun givenTaskExists(task: TaskEntity, isIdNull: Boolean = false) {
        val id = if (isIdNull) null else task.id
        taskEntityQueries.insertTask(id, task.position, task.name, task.color)
    }
}
