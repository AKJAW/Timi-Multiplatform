package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
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

    fun clickColorToggle() {
        composeTestRule.onNodeWithText("Color").performClick()
    }

    @ExperimentalTestApi
    fun selectAColorAt(index: Int) {
        val allColors = composeTestRule.onAllNodesWithTag("ColorPickerItem")
        allColors[0].onParent().performScrollToIndex(index)
        composeTestRule.mainClock.advanceTimeBy(100)
        allColors[index].performClick()
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
