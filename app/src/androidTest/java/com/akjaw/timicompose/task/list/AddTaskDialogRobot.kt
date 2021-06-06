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

class AddTaskDialogRobot(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun enterTaskName(name: String) {
        composeTestRule
            .onNodeWithText("Task name")
            .performTextInput(name)
    }

    // TODO this is flaky, revise later
    fun clickColorToggle() {
        composeTestRule.onNodeWithText("Color").performClick()
        Thread.sleep(50)
    }

    @ExperimentalTestApi
    fun selectAColorAt(index: Int) {
        composeTestRule.onNodeWithTag("ColorPicker").performScrollToIndex(index)
        composeTestRule.mainClock.advanceTimeBy(500)
        composeTestRule.onNodeWithTag("ColorPicker").onChildren()[index].performClick()
    }

    fun confirm() {
        composeTestRule.onNodeWithText("Add").performClick()
    }

    // TODO revise when additional Compose API will be added
    fun cancel() {
        Espresso.pressBack()
        Espresso.pressBack()
        Espresso.pressBack()
    }
}
