package com.akjaw.timi.android.app

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class BottomNavRobot(private val composeTestRule: ActivityComposeTestRule) {

    fun navigateToTasks() {
        composeTestRule.onNodeWithText("Tasks").performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }

    fun navigateToStopwatch() {
        composeTestRule.onNodeWithText("Stopwatch").performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }

    fun navigateToSettings() {
        composeTestRule.onNodeWithText("Settings").performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }
}
