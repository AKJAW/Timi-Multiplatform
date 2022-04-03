package com.akjaw.timicompose.settings

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.akjaw.settings.R
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavRobot
import com.akjaw.timicompose.allKoinModules
import com.akjaw.timicompose.composition.testModule
import com.akjaw.timicompose.createBaseTestRule
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore
class SettingsDarkModeTest {

    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @get:Rule
    val baseRule = createBaseTestRule(
        composeTestRule,
        *allKoinModules.toTypedArray(),
        testModule,
    )

    private lateinit var bottomNavRobot: BottomNavRobot
    private lateinit var settingsScreenRobot: SettingsScreenRobot
    private lateinit var settingsScreenVerifier: SettingsScreenVerifier

    private val darkModeText by lazy {
        composeTestRule.activity.getString(R.string.boolean_dark_mode)
    }

    @Before
    fun setUp() {
        bottomNavRobot = BottomNavRobot(composeTestRule)
        settingsScreenRobot = SettingsScreenRobot(composeTestRule)
        settingsScreenVerifier = SettingsScreenVerifier(composeTestRule)
    }

    @Test
    fun changingDarkSettingUpdatesTheBackground() {
        bottomNavRobot.navigateToSettings()
        assertDarkModeDisabled()

        settingsScreenRobot.clickTheDarkModeSwitch()

        assertDarkModeEnabled()
    }

    @Test
    fun changingDarkSettingUpdatesTheSwitch() {
        bottomNavRobot.navigateToSettings()
        settingsScreenVerifier.assertDarkModeSwitch(isSet = false)

        settingsScreenRobot.clickTheDarkModeSwitch()

        settingsScreenVerifier.assertDarkModeSwitch(isSet = true)
    }

    @Test
    fun theDarkSettingIsPersisted() {
        bottomNavRobot.navigateToSettings()
        settingsScreenRobot.clickTheDarkModeSwitch()

        composeTestRule.activityRule.scenario.recreate()

        settingsScreenVerifier.assertDarkModeSwitch(isSet = true)
        assertDarkModeEnabled()
    }

    private fun assertDarkModeEnabled() {
        assertFirstPixelColor(
            node = composeTestRule.onNodeWithText(darkModeText),
            expectedHexColor = "ff121212"
        )
    }

    private fun assertDarkModeDisabled() {
        assertFirstPixelColor(
            node = composeTestRule.onNodeWithText(darkModeText),
            expectedHexColor = "ffffffff"
        )
    }

    @Deprecated("Use the extension function from ColorAssertions")
    private fun assertFirstPixelColor(node: SemanticsNodeInteraction, expectedHexColor: String) {
        val imageBitmap = node.captureToImage()
        val color = imageBitmap.asAndroidBitmap().getPixel(0, 0)
        val actualHexColor = Integer.toHexString(color)
        try {
            assert(actualHexColor == expectedHexColor)
        } catch (e: Throwable) {
            throw AssertionError("Expected color: $expectedHexColor but was $actualHexColor")
        }
    }
}
