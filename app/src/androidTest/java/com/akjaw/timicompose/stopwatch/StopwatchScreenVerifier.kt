package com.akjaw.timicompose.stopwatch

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import com.akjaw.timicompose.ActivityComposeTestRule

class StopwatchScreenVerifier(
    private val composeTestRule: ActivityComposeTestRule
) {

    fun confirmStopwatchForTaskExists(name: String) {
        composeTestRule
            .onNodeWithTag("StopwatchList")
            .assert(hasAnyDescendant(hasText(name)))
    }

    fun confirmStopwatchForTaskHasTime(name: String, timestamp: String) {
        TODO("Not yet implemented")
    }

    fun confirmStopwatchHasColor(name: String, color: Int) {
        TODO("Not yet implemented")
    }
}
