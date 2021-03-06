package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.R
import com.akjaw.timicompose.utils.getString

class StopwatchScreenVerifier(
    private val composeTestRule: ActivityComposeTestRule
) {

    private val addStopwatchText =
        composeTestRule.getString(R.string.stopwatch_screen_add_stopwatch)

    fun confirmStopwatchForTaskExists(taskName: String) {
        onStopwatchWithTaskName(taskName)
            .assertExists()
    }

    fun confirmStopwatchForTaskHasTime(taskName: String, timestamp: String) {
        onStopwatchWithTaskName(taskName)
            .assert(hasAnyDescendant(hasText(timestamp)))
    }

    fun confirmAddButtonAtIndex(index: Int) {
        val addButton = composeTestRule
            .onNodeWithTag("StopwatchList")
            .onChildren()
            .get(index)

        addButton.assert(hasAnyChild(hasText(addStopwatchText)))
    }

    // After stopping the stopwatch is still in the ist but it is not displayed
    fun confirmStopwatchForTaskDoesNotExists(taskName: String) {
        val nodes = composeTestRule
            .onNodeWithTag("StopwatchList")
            .onChildren()
            .filter(hasAnyDescendant(hasText(taskName)))

        nodes.assertCountEquals(1)
        nodes.get(0).assertIsNotDisplayed()
    }

    private fun onStopwatchWithTaskName(name: String): SemanticsNodeInteraction =
        composeTestRule
            .onNodeWithTag("StopwatchList")
            .onChildren()
            .filterToOne(hasAnyChild(hasText(name)))
}
