package com.akjaw.timi.android.app.utils

import android.util.Log
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

// TODO won't work until a way to launch the activity in @Before is found
class RetryRule(private val retryCount: Int) : TestRule {

    companion object {
        private const val TAG = "RetryRule"
    }

    override fun apply(base: Statement, description: Description): Statement {
        return statement(base, description)
    }

    private fun statement(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                var caughtThrowable: Throwable? = null
                for (i in 0 until retryCount) {
                    try {
                        base.evaluate()
                        return
                    } catch (t: Throwable) {
                        caughtThrowable = t
                        Log.e(TAG, "${description.displayName}: run ${i + 1} failed")
                    }
                }
                Log.e(
                    TAG,
                    "${description.displayName}: Giving up after $retryCount failures"
                )
                throw caughtThrowable!!
            }
        }
    }
}
