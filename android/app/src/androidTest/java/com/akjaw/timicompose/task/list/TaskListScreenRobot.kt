package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.R
import com.akjaw.timicompose.utils.getString

class TaskListScreenRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    private val deleteTaskButtonDescription = composeTestRule.getString(R.string.task_list_top_bar_delete_description)

    fun clickOnFab() {
        composeTestRule.onNodeWithTag("AddTaskFab").performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }

    fun selectTaskWithName(name: String) {
        composeTestRule.onNodeWithText(name).performGesture { longClick() }
    }

    fun clickDeleteIcon() {
        composeTestRule.onNodeWithContentDescription(deleteTaskButtonDescription).performClick()
    }
}
