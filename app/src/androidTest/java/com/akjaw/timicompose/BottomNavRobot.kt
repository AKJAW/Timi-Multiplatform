package com.akjaw.timicompose

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class BottomNavRobot(private val composeTestRule: ActivityComposeTestRule) {

    fun navigateToTasks() {
        composeTestRule.onNodeWithText("Tasks").performClick()
    }

    fun navigateToStopwatch() {
        composeTestRule.onNodeWithText("Stopwatch").performClick()
    }

    fun navigateToSettings() {
        composeTestRule.onNodeWithText("Settings").performClick()
    }
}
