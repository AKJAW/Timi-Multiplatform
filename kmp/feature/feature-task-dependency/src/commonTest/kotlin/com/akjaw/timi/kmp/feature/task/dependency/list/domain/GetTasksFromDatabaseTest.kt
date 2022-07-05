package com.akjaw.timi.kmp.feature.task.dependency.list.domain

import com.akjaw.timi.kmp.feature.task.api.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.domain.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.composition.databaseModule
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.database.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.dependency.list.composition.taskListModule
import com.squareup.sqldelight.db.SqlDriver
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetTasksFromDatabaseTest : KoinComponent {

    private val taskEntityQueries: TaskEntityQueries by inject()
    private val systemUnderTest: GetTasks by inject()

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
    fun `The task is correctly mapped from the database`(): Unit = runBlocking {
        taskEntityQueries.insertTask(
            id = 1,
            position = 0,
            name = "name",
            color = TaskColor(22f, 22f, 22f)
        )
        taskEntityQueries.insertTask(
            id = 2,
            position = 0,
            name = "name2",
            color = TaskColor(33f, 33f, 33f)
        )

        val result = systemUnderTest.execute().first()

        assertSoftly {
            result shouldHaveSize 2
            result[0] shouldBe Task(
                id = 1,
                name = "name",
                backgroundColor = TaskColor(22f, 22f, 22f),
                isSelected = false
            )
            result[1] shouldBe Task(
                id = 2,
                name = "name2",
                backgroundColor = TaskColor(33f, 33f, 33f),
                isSelected = false
            )
        }
    }
}
