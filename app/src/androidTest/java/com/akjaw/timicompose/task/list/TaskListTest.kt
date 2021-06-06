package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.task.TaskEntityQueries
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavVerifier
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TaskListTest {

    @Inject
    lateinit var taskEntityQueries: TaskEntityQueries

    private lateinit var bottomNavVerifier: BottomNavVerifier
    private lateinit var addTaskDialogRobot: AddTaskDialogRobot
    private lateinit var taskListScreenRobot: TaskListScreenRobot
    private lateinit var taskListScreenVerifier: TaskListScreenVerifier

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()

        bottomNavVerifier = BottomNavVerifier(composeTestRule)
        taskListScreenRobot = TaskListScreenRobot(composeTestRule)
        addTaskDialogRobot = AddTaskDialogRobot(composeTestRule)
        taskListScreenVerifier = TaskListScreenVerifier(composeTestRule)

        taskEntityQueries.transaction {
            taskEntityQueries.selectAllTasks().executeAsList().forEach { task ->
                taskEntityQueries.deleteTaskById(task.id)
            }
        }
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
