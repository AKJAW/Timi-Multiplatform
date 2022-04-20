package com.akjaw.timi.android.app.settings

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timi.android.app.ActivityComposeTestRule
import com.akjaw.timi.android.feature.settings.ui.R

class SettingsScreenRobot(private val composeTestRule: ActivityComposeTestRule) {

    private val darkModeText by lazy {
        composeTestRule.activity.getString(R.string.boolean_dark_mode)
    }

    fun clickTheDarkModeSwitch() {
        composeTestRule.onNodeWithText(darkModeText).performClick()
        composeTestRule.mainClock.advanceTimeBy(500)
    }
}
