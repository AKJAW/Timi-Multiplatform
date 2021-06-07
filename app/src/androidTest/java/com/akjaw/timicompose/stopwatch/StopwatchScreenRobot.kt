package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timicompose.ActivityComposeTestRule

class StopwatchScreenRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun clickAddButton() {
        composeTestRule.onNodeWithText("Add a new stopwatch").performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }

    fun pauseStopwatchForTask(taskName: String) {
        selectStopwatchWithTaskName(taskName).onChildren().filterToOne(hasText("Pause")).performClick()
    }
}
