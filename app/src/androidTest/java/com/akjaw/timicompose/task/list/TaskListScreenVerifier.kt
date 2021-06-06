package com.akjaw.timicompose.task.list

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.hasMainColor

class TaskListScreenVerifier(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun confirmTaskWithNameExists(name: String) {
        composeTestRule
            .onNodeWithTag("TaskList")
            .assert(hasAnyDescendant(hasText(name)))
    }

    fun confirmTaskWithNameHasTheCorrectColor(name: String, color: String) {
        selectTaskWithName(name)
            .hasMainColor(color)
    }

    // TODO revise when additional Compose API will be added
    fun confirmTaskWithNameDoesNotExists(name: String) {
        val nodes = composeTestRule
            .onNodeWithTag("TaskList")
            .onChildren()
            .filter(hasAnyDescendant(hasText(name)))

        assert(nodes.fetchSemanticsNodes(atLeastOneRootRequired = false).isEmpty()) {
            "The task with the name: $name exists but shouldn't"
        }
    }

    fun confirmTaskWithNameSelected(name: String, color: Color) {
        val task = selectTaskWithName(name)

        val taskImage = task.captureToImage()
        val right = taskImage.width - 1
        val verticalMiddle = taskImage.height / 2
        val actualColor = taskImage.asAndroidBitmap().getPixel(right, verticalMiddle)

        assert(actualColor == color.toArgb()) {
            "Expected color: $color but was $actualColor"
        }
    }

    private fun selectTaskWithName(name: String) =
        composeTestRule
            .onNodeWithTag("TaskList")
            .onChildren()
            .filterToOne(hasAnyChild(hasText(name)))
}
