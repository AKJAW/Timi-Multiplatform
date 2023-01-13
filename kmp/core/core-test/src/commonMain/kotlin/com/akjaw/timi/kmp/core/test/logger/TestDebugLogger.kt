package com.akjaw.timi.kmp.core.test.logger

import com.akjaw.timi.kmp.core.shared.logger.DebugLogger

class TestDebugLogger : DebugLogger {

    override fun log(tag: String, message: String) {
        println("$tag: $message")
    }

    override fun error(tag: String, throwable: Throwable) {
        println("$tag: ${throwable.stackTraceToString()}")
    }
}