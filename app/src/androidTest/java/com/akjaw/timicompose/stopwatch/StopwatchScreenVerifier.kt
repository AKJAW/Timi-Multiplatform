package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.akjaw.timicompose.ActivityComposeTestRule

class StopwatchScreenVerifier(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun confirmStopwatchForTaskExists(taskName: String) {
        selectStopwatchWithTaskName(taskName)
            .assertExists()
    }

    fun confirmStopwatchForTaskHasTime(taskName: String, timestamp: String) {
        selectStopwatchWithTaskName(taskName)
            .assert(hasAnyDescendant(hasText(timestamp)))
    }

    fun confirmAddButtonAtIndex(index: Int) {
        val addButton = composeTestRule
            .onNodeWithTag("StopwatchList")
            .onChildren()
            .get(index)

        addButton.assert(hasAnyChild(hasText("Add a new stopwatch")))
    }

    private fun selectStopwatchWithTaskName(name: String): SemanticsNodeInteraction =
        composeTestRule
            .onNodeWithTag("StopwatchList")
            .onChildren()
            .filterToOne(hasAnyChild(hasText(name)))
}
