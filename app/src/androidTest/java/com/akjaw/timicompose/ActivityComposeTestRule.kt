package com.akjaw.timicompose

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.akjaw.timicompose.utils.HiltInjectionRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.RuleChain

typealias ActivityComposeTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun createBaseTestRule(hiltRule: HiltAndroidRule, composeRule: ActivityComposeTestRule): RuleChain =
    RuleChain
        .outerRule(hiltRule)
        .around(HiltInjectionRule(hiltRule))
        .around(composeRule)
