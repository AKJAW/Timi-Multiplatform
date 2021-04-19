package com.akjaw.task.list.presentation

import androidx.compose.ui.graphics.Color
import com.akjaw.task.api.data.TaskRepository
import com.akjaw.task.api.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class TaskListViewModelTest {

    companion object {
        val TASK1 = Task(
            id = 0,
            name = "name",
            backgroundColor = Color.White,
            isSelected = false,
        )
        val TASK2 = Task(
            id = 1,
            name = "name2",
            backgroundColor = Color.White,
            isSelected = false,
        )
    }

    private lateinit var taskRepository: TaskRepositoryFake
    private lateinit var systemUnderTest: TaskListViewModel

    @BeforeEach
    fun setUp() {
        taskRepository = TaskRepositoryFake()
        systemUnderTest = TaskListViewModel(
            taskRepository = taskRepository,
        )
    }

    @Test
    fun `Adding a task changes the list`() = runBlockingTest {
        taskRepository.init(listOf(TASK1))
        systemUnderTest.addTask(TASK2)

        val result = systemUnderTest.tasks.first()

        expectThat(result).isEqualTo(listOf(TASK1, TASK2))
    }

    @Test
    fun `Deleting tasks changes the list`() = runBlockingTest {
        taskRepository.init(listOf(TASK1, TASK2))
        systemUnderTest.deleteTasks(listOf(TASK1, TASK2))

        val result = systemUnderTest.tasks.first()

        expectThat(result).isEqualTo(emptyList())

    }
}

class TaskRepositoryFake : TaskRepository {

    override var tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())

    fun init(tasks: List<Task>) {
        this.tasks = MutableStateFlow(tasks)
    }

    override fun addTask(taskToBeAdded: Task) {
        tasks.value = tasks.value + taskToBeAdded
    }

    override fun deleteTasks(tasksToBeDeleted: List<Task>) {
        val newTasks = tasks.value.filterNot { task -> tasksToBeDeleted.contains(task) }
        tasks.value = newTasks
    }
}
