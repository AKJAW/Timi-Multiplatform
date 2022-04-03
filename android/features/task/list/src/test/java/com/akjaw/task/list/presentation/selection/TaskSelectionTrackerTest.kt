package com.akjaw.task.list.presentation.selection

import com.akjaw.timi.kmp.feature.task.api.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskSelectionTrackerTest {

    companion object {
        val TASK1 = Task(id = 0, name = "one")
        val TASK2 = Task(id = 1, name = "two")
    }

    private lateinit var originalFlow: MutableStateFlow<List<Task>>
    private lateinit var systemUnderTest: TaskSelectionTracker

    @BeforeEach
    fun setUp() {
        originalFlow = MutableStateFlow(emptyList())
        systemUnderTest = TaskSelectionTracker(originalFlow)
    }

    @Test
    fun `Selecting a task from the original flow sets the correct flag`() = runTest {
        givenTasks(TASK1, TASK2)

        systemUnderTest.toggleTask(TASK1)

        val result = systemUnderTest.tasksWithSelection.first()
        val task = result.first()
        expectThat(task).isEqualTo(TASK1.copy(isSelected = true))
    }

    @Test
    fun `Unselecting a task from the original flow sets the correct flag`() = runTest {
        val task = TASK2.copy(isSelected = true)
        givenTasks(TASK1, task)

        systemUnderTest.toggleTask(task)

        val result = systemUnderTest.tasksWithSelection.first().last()
        expectThat(result).isEqualTo(TASK2)
    }

    @Test
    fun `Task list change is reflected`() = runTest {
        givenTasks(TASK1, TASK2)
        systemUnderTest.toggleTask(TASK2)
        givenTasks(TASK1)

        val result = systemUnderTest.tasksWithSelection.first()
        expectThat(result).hasSize(1)
    }

    private fun givenTasks(vararg tasks: Task) {
        originalFlow.value = listOf(*tasks)
    }
}
