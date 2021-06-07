package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timicompose.ActivityComposeTestRule

class AddStopwatchDialogRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun selectTaskWithName(name: String) {
        composeTestRule.onNodeWithText(name).performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }
}
