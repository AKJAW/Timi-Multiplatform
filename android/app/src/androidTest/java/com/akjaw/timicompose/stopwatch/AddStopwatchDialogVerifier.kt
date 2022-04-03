package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.onAllNodesWithTag
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.utils.assertDescendantDoesNotContainText

class AddStopwatchDialogVerifier(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun confirmTaskListHasSize(amount: Int) {
        val nodes = composeTestRule
            .onAllNodesWithTag("AddStopwatchTaskItem")
            .fetchSemanticsNodes()

        assert(nodes.count() == amount) {
            "Expected $amount tasks but got ${nodes.count()}"
        }
    }

    fun confirmTaskListDoesNotContain(taskName: String) {
        composeTestRule
            .onAllNodesWithTag("AddStopwatchTaskItem")
            .assertDescendantDoesNotContainText(taskName)
    }
}
