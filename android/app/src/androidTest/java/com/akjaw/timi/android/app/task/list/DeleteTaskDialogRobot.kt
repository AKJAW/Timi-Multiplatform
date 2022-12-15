package com.akjaw.timi.android.app.task.list

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timi.android.app.ActivityComposeTestRule
import com.akjaw.timi.android.app.utils.getString
import com.akjaw.timicompose.R

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
