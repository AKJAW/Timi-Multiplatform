package com.akjaw.timicompose.task.list

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
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
            .onChildren()
            .assertAny(hasAnyChild(hasText(name)))
    }

    fun confirmTaskWithNameHasTheCorrectColor(name: String, color: String) {
        composeTestRule
            .onNodeWithTag("TaskList")
            .onChildren()
            .filterToOne(hasAnyChild(hasText(name)))
            .hasMainColor(color)
    }
}
