package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.akjaw.timicompose.ActivityComposeTestRule

class TaskListScreenRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun clickOnFab() {
        composeTestRule.onNodeWithTag("AddTaskFab").performClick()
    }
}
