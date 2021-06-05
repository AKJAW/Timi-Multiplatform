package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavVerifier
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class TaskListTest {

    private lateinit var bottomNavVerifier: BottomNavVerifier
    private lateinit var addTaskDialogRobot: AddTaskDialogRobot
    private lateinit var taskListScreenRobot: TaskListScreenRobot
    private lateinit var taskListScreenVerifier: TaskListScreenVerifier

    @get:Rule
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    // TODO inject inMemoryDatabase

    @Before
    fun setUp() {
        bottomNavVerifier = BottomNavVerifier(composeTestRule)
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

    @ExperimentalTestApi
    @Test
    fun cancellingTheTaskAddDoesNotModifyTheList() {
        taskListScreenRobot.clickOnFab()
        addTaskDialogRobot.enterTaskName("Example")
        addTaskDialogRobot.clickColorToggle()
        addTaskDialogRobot.selectAColorAt(2)

        addTaskDialogRobot.cancel()

        taskListScreenVerifier.confirmTaskWithNameDoesNotExists("Example")
    }

    // TODO move out to a bottom nav test
    @Test
    fun taskListIsVisibleOnStartUp() {
        bottomNavVerifier.confirmTasksSelected()
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
