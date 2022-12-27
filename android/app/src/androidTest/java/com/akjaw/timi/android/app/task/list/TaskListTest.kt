package com.akjaw.timi.android.app.task.list

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.timi.android.app.ActivityComposeTestRule
import com.akjaw.timi.android.app.BottomNavVerifier
import com.akjaw.timi.android.app.allKoinModules
import com.akjaw.timi.android.app.composition.testModule
import com.akjaw.timi.android.app.createBaseTestRule
import com.akjaw.timi.android.core.view.toTaskColor
import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class TaskListTest : KoinTest {

    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @get:Rule
    val baseRule = createBaseTestRule(
        composeTestRule,
        *allKoinModules.toTypedArray(),
        testModule
    )

    private val taskEntityQueries: TaskEntityQueries by inject()

    private lateinit var bottomNavVerifier: BottomNavVerifier
    private lateinit var addTaskDialogRobot: AddTaskDialogRobot
    private lateinit var deleteTaskDialogRobot: DeleteTaskDialogRobot
    private lateinit var taskListScreenRobot: TaskListScreenRobot
    private lateinit var taskListScreenVerifier: TaskListScreenVerifier

    @Before
    fun setUp() {
        bottomNavVerifier = BottomNavVerifier(composeTestRule)
        taskListScreenRobot = TaskListScreenRobot(composeTestRule)
        addTaskDialogRobot = AddTaskDialogRobot(composeTestRule)
        deleteTaskDialogRobot = DeleteTaskDialogRobot(composeTestRule)
        taskListScreenVerifier = TaskListScreenVerifier(composeTestRule)
    }

    @ExperimentalTestApi
    @Test
    fun addedTaskIsPresentInTheList() {
        taskListScreenRobot.clickOnFab()
        addTaskDialogRobot.enterTaskName("Example")
        addTaskDialogRobot.clickColorToggle()
        addTaskDialogRobot.selectAColorAt(4)

        addTaskDialogRobot.confirm()

        taskListScreenVerifier.confirmTaskWithNameExists("Example")
        taskListScreenVerifier.confirmTaskWithNameHasTheCorrectColor("Example", "ff84a2ef")
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
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = "Existing",
            color = Color.Magenta.toTaskColor()
        )
        taskListScreenRobot.selectTaskWithName("Existing")
        taskListScreenRobot.clickDeleteIcon()

        deleteTaskDialogRobot.confirm()

        taskListScreenVerifier.confirmTaskWithNameDoesNotExists("Existing")
    }

    @Test
    fun cancellingTheTaskRemovalDoesNotModifyTheList() {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = "Existing",
            color = Color.Magenta.toTaskColor()
        )
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
            color = Color.Magenta.toTaskColor()
        )

        taskListScreenRobot.selectTaskWithName("Existing")

        taskListScreenVerifier.confirmTaskWithNameSelected(
            name = "Existing",
            color = Color.Magenta
        )
    }
}
