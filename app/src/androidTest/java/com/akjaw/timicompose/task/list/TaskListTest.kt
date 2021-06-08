package com.akjaw.timicompose.task.list

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.task.TaskEntityQueries
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavVerifier
import com.akjaw.timicompose.utils.clearDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TaskListTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @Inject
    lateinit var taskEntityQueries: TaskEntityQueries

    private lateinit var bottomNavVerifier: BottomNavVerifier
    private lateinit var addTaskDialogRobot: AddTaskDialogRobot
    private lateinit var deleteTaskDialogRobot: DeleteTaskDialogRobot
    private lateinit var taskListScreenRobot: TaskListScreenRobot
    private lateinit var taskListScreenVerifier: TaskListScreenVerifier

    @Before
    fun setUp() {
        hiltRule.inject()

        bottomNavVerifier = BottomNavVerifier(composeTestRule)
        taskListScreenRobot = TaskListScreenRobot(composeTestRule)
        addTaskDialogRobot = AddTaskDialogRobot(composeTestRule)
        deleteTaskDialogRobot = DeleteTaskDialogRobot(composeTestRule)
        taskListScreenVerifier = TaskListScreenVerifier(composeTestRule)
    }

    @After
    fun tearDown() {
        taskEntityQueries.clearDatabase()
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

    @Test
    fun taskListIsVisibleOnStartUp() {
        bottomNavVerifier.confirmTasksSelected()
    }

    @Test
    fun deletingATaskRemovesItFromTheList() {
        taskEntityQueries.insertTask(id = null, position = 0, name = "Existing", color = 0)
        taskListScreenRobot.selectTaskWithName("Existing")
        taskListScreenRobot.clickDeleteIcon()

        deleteTaskDialogRobot.confirm()

        taskListScreenVerifier.confirmTaskWithNameDoesNotExists("Existing")
    }

    @Test
    fun cancellingTheTaskRemovalDoesNotModifyTheList() {
        taskEntityQueries.insertTask(id = null, position = 0, name = "Existing", color = 0)
        taskListScreenRobot.selectTaskWithName("Existing")
        taskListScreenRobot.clickDeleteIcon()

        deleteTaskDialogRobot.cancel()

        taskListScreenVerifier.confirmTaskWithNameExists("Existing")
    }

    @Test
    fun selectingATaskMakesTheColorFill() {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = "Existing",
            color = Color.Magenta.toArgb()
        )

        taskListScreenRobot.selectTaskWithName("Existing")

        taskListScreenVerifier.confirmTaskWithNameSelected(
            name = "Existing",
            color = Color.Magenta
        )
    }
}
