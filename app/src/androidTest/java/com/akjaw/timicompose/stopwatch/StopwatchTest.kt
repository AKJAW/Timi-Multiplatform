package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akjaw.task.TaskEntityQueries
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavRobot
import com.akjaw.timicompose.composition.TimestampProviderStub
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

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

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
        hiltRule.inject()

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

    @Ignore
    @Test
    fun multipleStopwatchesCanBeStaredAndAreUpdated() {
    }

    @Ignore
    @Test
    fun pausingAStopwatchPreventsItFromUpdatingTheTime() {
    }

    @Ignore
    @Test
    fun theStopwatchRemainsRunningWhenChangingTheBottomNav() {
    }

    @Ignore
    @Test
    fun pausingAndResumingTheStopwatchWorksCorrectly() {
    }

    @Ignore
    @Test
    fun stoppingTheStopwatchRemovesItFromTheList() {
    }

    private fun addTask(name: String, color: Int) {
        taskEntityQueries.insertTask(id = null, position = 0, name = name, color = color)
    }
}
