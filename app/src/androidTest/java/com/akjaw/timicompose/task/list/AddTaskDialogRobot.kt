package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.R
import com.akjaw.timicompose.utils.getString

class AddTaskDialogRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    private val taskNameInput = composeTestRule.getString(R.string.task_list_fab_task_name)
    private val colorToggle = composeTestRule.getString(R.string.task_list_fab_color)
    private val addTaskButton = composeTestRule.getString(R.string.task_list_fab_add)

    fun enterTaskName(name: String) {
        composeTestRule
            .onNodeWithText(taskNameInput)
            .performTextInput(name)
    }

    // TODO this is flaky, revise later
    fun clickColorToggle() {
        composeTestRule.onNodeWithText(colorToggle).performClick()
        Thread.sleep(50)
    }

    @ExperimentalTestApi
    fun selectAColorAt(index: Int) {
        composeTestRule.onNodeWithTag("ColorPicker").performScrollToIndex(index)
        composeTestRule.mainClock.advanceTimeBy(500)
        composeTestRule.onNodeWithTag("ColorPicker").onChildren()[index].performClick()
    }

    fun confirm() {
        composeTestRule.onNodeWithText(addTaskButton).performClick()
    }

    // TODO revise when additional Compose API will be added
    fun cancel() {
        Espresso.pressBack()
    }
}
