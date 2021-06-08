package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
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
        selectStopwatchWithTaskName(taskName)
            .onStopwatchButtonWithDescription("Pause")
            .performClick()
    }

    fun resumeStopwatchForTask(taskName: String) {
        selectStopwatchWithTaskName(taskName)
            .onStopwatchButtonWithDescription("Start")
            .performClick()
    }

    fun stopStopwatchForTask(taskName: String) {
        selectStopwatchWithTaskName(taskName)
            .onStopwatchButtonWithDescription("Stop")
            .performClick()
    }

    private fun SemanticsNodeInteraction.onStopwatchButtonWithDescription(
        text: String
    ): SemanticsNodeInteraction =
        this.onChildren().filterToOne(hasContentDescription(text))

    // TODO this is duplicated, can this be extracted to a generic extension function?
    private fun selectStopwatchWithTaskName(name: String): SemanticsNodeInteraction =
        composeTestRule
            .onNodeWithTag("StopwatchList")
            .onChildren()
            .filterToOne(hasAnyChild(hasText(name)))
}
