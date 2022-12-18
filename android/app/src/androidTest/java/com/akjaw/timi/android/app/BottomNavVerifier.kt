package com.akjaw.timi.android.app

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag

class BottomNavVerifier(
    private val composeTestRule: ActivityComposeTestRule
) {

    private enum class TAB(val text: String) {
        TASKS("Tasks"),
        STOPWATCH("Stopwatch"),
        SETTINGS("Settings")
    }

    fun confirmTasksSelected() {
        assertTabIsSelected(TAB.TASKS)
    }

    private fun assertTabIsSelected(tab: TAB) {
        composeTestRule.onNodeWithTag("BottomNav")
            .onChild()
            .onChildren()
            .filterToOne(isSelected())
            .assert(hasText(tab.text))
    }
}
