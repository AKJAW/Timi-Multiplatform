package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.R
import com.akjaw.timicompose.utils.getString

class DeleteTaskDialogRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    private val confirm = composeTestRule.getString(R.string.task_list_top_bar_confirm)
    private val cancel = composeTestRule.getString(R.string.task_list_top_bar_cancel)

    fun confirm() {
        composeTestRule.onNodeWithText(confirm).performClick()
    }

    fun cancel() {
        composeTestRule.onNodeWithText(cancel).performClick()
    }
}
