package com.akjaw.timi.android.app.stopwatch

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timi.android.app.ActivityComposeTestRule
import com.akjaw.timi.android.app.utils.getString
import com.akjaw.timicompose.R

class StopwatchScreenRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    private val addStopwatchText =
        composeTestRule.getString(R.string.stopwatch_screen_add_stopwatch)
    private val pauseText = composeTestRule.getString(R.string.stopwatch_screen_pause)
    private val startText = composeTestRule.getString(R.string.stopwatch_screen_start)
    private val stopText = composeTestRule.getString(R.string.stopwatch_screen_stop)

    fun clickAddButton() {
        composeTestRule.onNodeWithText(addStopwatchText).performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }

    fun pauseStopwatchForTask(taskName: String) {
        selectStopwatchWithTaskName(taskName)
            .onStopwatchButtonWithDescription(pauseText)
            .performClick()
    }

    fun resumeStopwatchForTask(taskName: String) {
        selectStopwatchWithTaskName(taskName)
            .onStopwatchButtonWithDescription(startText)
            .performClick()
    }

    fun stopStopwatchForTask(taskName: String) {
        selectStopwatchWithTaskName(taskName)
            .onStopwatchButtonWithDescription(stopText)
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
