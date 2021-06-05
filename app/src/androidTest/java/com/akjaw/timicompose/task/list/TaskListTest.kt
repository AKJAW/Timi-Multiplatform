package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.timicompose.ActivityComposeTestRule
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class TaskListTest {

    private lateinit var addTaskDialogRobot: AddTaskDialogRobot
    private lateinit var taskListScreenRobot: TaskListScreenRobot
    private lateinit var taskListScreenVerifier: TaskListScreenVerifier

    @get:Rule
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    // TODO inject inMemoryDatabase

    @Before
    fun setUp() {
        taskListScreenRobot = TaskListScreenRobot(composeTestRule)
        addTaskDialogRobot = AddTaskDialogRobot(composeTestRule)
        taskListScreenVerifier = TaskListScreenVerifier(composeTestRule)
    }

    @ExperimentalTestApi
    @Test
    fun addedTaskIsPresentInTheList() {
        taskListScreenRobot.clickOnFab()
        addTaskDialogRobot.enterTaskName("Example")
        addTaskDialogRobot.clickColorToggle()
        addTaskDialogRobot.selectAColorAt(8)

        addTaskDialogRobot.confirm()

        taskListScreenVerifier.confirmTaskWithNameExists("Example")
        taskListScreenVerifier.confirmTaskWithNameHasTheCorrectColor("Example", "ff84efd4")
    }

    @Ignore
    @Test
    fun cancellingTheTaskAddDoesNotModifyTheList() {
    }

    @Ignore
    @Test
    fun taskListIsVisibleOnStartUp() {
    }

    @Ignore
    @Test
    fun deletingATaskRemovesItFromTheList() {
    }

    @Ignore
    @Test
    fun selectingATaskMakesTheColorFill() {
    }
}
