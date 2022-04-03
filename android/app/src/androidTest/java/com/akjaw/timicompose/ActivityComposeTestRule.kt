package com.akjaw.timicompose

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adevinta.android.barista.rule.cleardata.ClearDatabaseRule
import com.adevinta.android.barista.rule.cleardata.ClearPreferencesRule
import com.akjaw.timicompose.utils.KoinLoadModulesTestRule
import org.junit.rules.RuleChain
import org.koin.core.module.Module

typealias ActivityComposeTestRule =
    AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun createBaseTestRule(composeRule: ActivityComposeTestRule, vararg modules: Module): RuleChain =
    RuleChain
        .outerRule(KoinLoadModulesTestRule(*modules))
        .around(ClearPreferencesRule())
        .around(ClearDatabaseRule())
        .around(composeRule)
