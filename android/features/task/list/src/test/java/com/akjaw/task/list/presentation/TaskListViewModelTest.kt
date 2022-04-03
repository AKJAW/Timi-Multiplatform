package com.akjaw.task.list.presentation

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.Database
import com.akjaw.task.list.DatabaseInteractorFactory
import com.akjaw.task.list.data.taskColorAdapter
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
import com.akjaw.timi.kmp.core.shared.coroutines.TestDispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.api.model.TaskColor
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskListViewModelTest {

    companion object {
        val TASK1 = Task(
            id = 0,
            name = "name",
            backgroundColor = TaskColor(22f, 22f, 22f),
            isSelected = false,
        )
        val TASK2 = Task(
            id = 1,
            name = "name2",
            backgroundColor = TaskColor(33f, 33f, 33f),
            isSelected = false,
        )
    }

    private lateinit var taskEntityQueries: TaskEntityQueries
    private val taskSelectionTrackerFactory = TaskSelectionTrackerFactory()
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private lateinit var systemUnderTest: TaskListViewModel

    @BeforeEach
    fun setUp() {
        val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            Database.Schema.create(this)
        }
        taskEntityQueries = Database(inMemorySqlDriver, taskColorAdapter).taskEntityQueries
        val factory = DatabaseInteractorFactory(taskEntityQueries)
        systemUnderTest = TaskListViewModel(
            getTasks = factory.createGetTasks(),
            deleteTasks = factory.createDeleteTasks(),
            addTask = factory.createAddTask(),
            dispatcherProvider = TestDispatcherProvider(testCoroutineDispatcher),
            taskSelectionTrackerFactory = taskSelectionTrackerFactory,
        )
    }

    @Test
    fun `Adding a task changes the list`(): Unit = runBlocking {
        givenTasks(TASK1)

        systemUnderTest.addTask(TASK2)

        val result = systemUnderTest.tasks.first()
        expectThat(result).isEqualTo(listOf(TASK1, TASK2))
    }

    @Test
    fun `Deleting tasks changes the list`(): Unit = runBlocking {
        givenTasks(TASK1, TASK2)

        systemUnderTest.deleteTasks(listOf(TASK1, TASK2))

        val result = systemUnderTest.tasks.first()
        expectThat(result).isEqualTo(emptyList())
    }

    private fun givenTasks(vararg task: Task) {
        task.forEach {
            taskEntityQueries.insertTask(
                id = it.id,
                position = 0,
                name = it.name,
                color = it.backgroundColor
            )
        }
    }
}
