package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.task.TaskEntityQueries
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavRobot
import com.akjaw.timicompose.composition.TimestampProviderStub
import com.akjaw.timicompose.createBaseTestRule
import com.akjaw.timicompose.task.list.DeleteTaskDialogRobot
import com.akjaw.timicompose.task.list.TaskListScreenRobot
import com.akjaw.timicompose.utils.clearDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class StopwatchTest {

    companion object {
        private const val FIRST_TASK_NAME = "Existing"
        private val FIRST_TASK_COLOR = Color.Magenta.toArgb()
        private const val SECOND_TASK_NAME = "Newer"
        private val SECOND_TASK_COLOR = Color.Blue.toArgb()
    }

    var hiltRule = HiltAndroidRule(this)
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @get:Rule(order = 0)
    val baseRule = createBaseTestRule(hiltRule, composeTestRule)

    @Inject
    lateinit var taskEntityQueries: TaskEntityQueries

    @Inject
    lateinit var timestampProviderStub: TimestampProviderStub

    private lateinit var bottomNavRobot: BottomNavRobot
    private lateinit var stopwatchScreenRobot: StopwatchScreenRobot
    private lateinit var addStopwatchDialogRobot: AddStopwatchDialogRobot
    private lateinit var stopwatchScreenVerifier: StopwatchScreenVerifier
    private lateinit var addStopwatchDialogVerifier: AddStopwatchDialogVerifier

    @Before
    fun setUp() {
        bottomNavRobot = BottomNavRobot(composeTestRule).apply { navigateToStopwatch() }
        stopwatchScreenRobot = StopwatchScreenRobot(composeTestRule)
        addStopwatchDialogRobot = AddStopwatchDialogRobot(composeTestRule)
        stopwatchScreenVerifier = StopwatchScreenVerifier(composeTestRule)
        addStopwatchDialogVerifier = AddStopwatchDialogVerifier(composeTestRule)

        addTask(name = FIRST_TASK_NAME, color = FIRST_TASK_COLOR)
        addTask(name = SECOND_TASK_NAME, color = SECOND_TASK_COLOR)
    }

    @After
    fun tearDown() {
        taskEntityQueries.clearDatabase()
    }

    @Test
    fun addingAStopwatchStartsTheStopwatchInTheList() {
        timestampProviderStub.currentMilliseconds = 0
        stopwatchScreenRobot.clickAddButton()

        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)

        timestampProviderStub.currentMilliseconds = 22000
        stopwatchScreenVerifier.confirmStopwatchForTaskExists(FIRST_TASK_NAME)
        stopwatchScreenVerifier.confirmStopwatchForTaskHasTime(
            taskName = FIRST_TASK_NAME,
            timestamp = "00:22:000"
        )
    }

    @Test
    fun afterAddingAStopwatchTheAddButtonIsUnderneath() {
        stopwatchScreenRobot.clickAddButton()

        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)

        stopwatchScreenVerifier.confirmAddButtonAtIndex(1)
    }

    @Test
    fun afterAddingAStopwatchItIsNotAvailableInTheDialogList() {
        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)

        stopwatchScreenRobot.clickAddButton()

        addStopwatchDialogVerifier.confirmTaskListHasSize(1)
        addStopwatchDialogVerifier.confirmTaskListDoesNotContain(taskName = FIRST_TASK_NAME)
    }

    @Test
    fun multipleStopwatchesCanBeStartedAndAreUpdated() {
        timestampProviderStub.currentMilliseconds = 0

        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)
        timestampProviderStub.currentMilliseconds = 30000

        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(SECOND_TASK_NAME)
        timestampProviderStub.currentMilliseconds = 60000

        Thread.sleep(50)
        stopwatchScreenVerifier.confirmStopwatchForTaskExists(FIRST_TASK_NAME)
        stopwatchScreenVerifier.confirmStopwatchForTaskHasTime(
            taskName = FIRST_TASK_NAME,
            timestamp = "01:00:000"
        )
        stopwatchScreenVerifier.confirmStopwatchForTaskExists(SECOND_TASK_NAME)
        stopwatchScreenVerifier.confirmStopwatchForTaskHasTime(
            taskName = SECOND_TASK_NAME,
            timestamp = "00:30:000"
        )
    }

    @Test
    fun pausingAStopwatchPreventsItFromUpdatingTheTime() {
        timestampProviderStub.currentMilliseconds = 0
        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)
        timestampProviderStub.currentMilliseconds = 30000

        stopwatchScreenRobot.pauseStopwatchForTask(taskName = FIRST_TASK_NAME)

        timestampProviderStub.currentMilliseconds = 60000
        Thread.sleep(50)
        stopwatchScreenVerifier.confirmStopwatchForTaskHasTime(
            taskName = FIRST_TASK_NAME,
            timestamp = "00:30:000"
        )
    }

    @Test
    fun pausingAndResumingTheStopwatchWorksCorrectly() {
        timestampProviderStub.currentMilliseconds = 0
        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)
        timestampProviderStub.currentMilliseconds = 30000
        stopwatchScreenRobot.pauseStopwatchForTask(taskName = FIRST_TASK_NAME)

        stopwatchScreenRobot.resumeStopwatchForTask(taskName = FIRST_TASK_NAME)

        timestampProviderStub.currentMilliseconds = 60000
        composeTestRule.mainClock.advanceTimeBy(300)
        stopwatchScreenVerifier.confirmStopwatchForTaskHasTime(
            taskName = FIRST_TASK_NAME,
            timestamp = "01:00:000"
        )
    }

    @Test
    fun stoppingTheStopwatchRemovesItFromTheList() {
        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)

        stopwatchScreenRobot.stopStopwatchForTask(taskName = FIRST_TASK_NAME)

        stopwatchScreenVerifier.confirmStopwatchForTaskDoesNotExists(taskName = FIRST_TASK_NAME)
    }

    @Ignore("Was broken by compose update")
    @Test
    fun theStopwatchRemainsRunningWhenChangingTheBottomNav() {
        timestampProviderStub.currentMilliseconds = 0
        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)
        bottomNavRobot.navigateToTasks()
        timestampProviderStub.currentMilliseconds = 30000

        bottomNavRobot.navigateToStopwatch()

        Thread.sleep(50)
        timestampProviderStub.currentMilliseconds = 60000
        Thread.sleep(50)
        stopwatchScreenVerifier.confirmStopwatchForTaskHasTime(
            taskName = FIRST_TASK_NAME,
            timestamp = "01:00:000"
        )
    }

    @Ignore("Not implemented yet")
    @Test
    fun removingATaskStopsTheStopwatchForThatTask() {
        val taskListScreenRobot = TaskListScreenRobot(composeTestRule)
        val deleteTaskDialogRobot = DeleteTaskDialogRobot(composeTestRule)

        timestampProviderStub.currentMilliseconds = 0
        stopwatchScreenRobot.clickAddButton()
        addStopwatchDialogRobot.selectTaskWithName(FIRST_TASK_NAME)

        bottomNavRobot.navigateToTasks()
        taskListScreenRobot.selectTaskWithName(FIRST_TASK_NAME)
        taskListScreenRobot.clickDeleteIcon()
        deleteTaskDialogRobot.confirm()

        bottomNavRobot.navigateToStopwatch()

        stopwatchScreenVerifier.confirmStopwatchForTaskDoesNotExists(taskName = FIRST_TASK_NAME)
    }

    private fun addTask(name: String, color: Int) {
        taskEntityQueries.insertTask(id = null, position = 0, name = name, color = color)
    }
}
