package com.akjaw.timicompose.settings

import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsDarkModeTest {

    private lateinit var bottomNavRobot: BottomNavRobot

    @get:Rule
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @Before
    fun setUp() {
        bottomNavRobot = BottomNavRobot(composeTestRule)
    }

    // TODO revise this test when it will be possible to assert colors
    @Test
    fun changingDarkSettingUpdatesTheSwitch() {
        bottomNavRobot.navigateToSettings()

        composeTestRule.onNodeWithText("Dark mode").performClick()

        composeTestRule.onNodeWithText("Dark mode").onChild().assertIsOn()
    }
}
