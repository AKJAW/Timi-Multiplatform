package com.akjaw.timi.android.app.stopwatch

import androidx.compose.ui.test.onAllNodesWithTag
import com.akjaw.timi.android.app.ActivityComposeTestRule
import com.akjaw.timi.android.app.utils.assertDescendantDoesNotContainText

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
