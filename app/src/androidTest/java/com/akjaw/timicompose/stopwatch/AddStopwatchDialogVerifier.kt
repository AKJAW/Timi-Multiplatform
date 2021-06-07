package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import com.akjaw.timicompose.ActivityComposeTestRule

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
        val nodes = composeTestRule
            .onAllNodesWithTag("AddStopwatchTaskItem")
            .filter(hasAnyDescendant(hasText(taskName))) // TODO extract with assertion

        assert(nodes.fetchSemanticsNodes(atLeastOneRootRequired = false).isEmpty()) {
            "The task with the name: $taskName exists but shouldn't"
        }
    }
}
