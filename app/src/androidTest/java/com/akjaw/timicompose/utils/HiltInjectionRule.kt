package com.akjaw.timicompose.utils

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class HiltInjectionRule(private val hiltRule: HiltAndroidRule) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return statement(base, description)
    }

    private fun statement(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                try {
                    hiltRule.inject()
                } catch (e: Throwable) {
                    /* Empty */
                }
                base.evaluate()
            }
        }
    }
}
