package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timicompose.ActivityComposeTestRule

class DeleteTaskDialogRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun confirm() {
        composeTestRule.onNodeWithText("Yes").performClick()
    }

    fun cancel() {
        composeTestRule.onNodeWithText("Cancel").performClick()
    }
}
