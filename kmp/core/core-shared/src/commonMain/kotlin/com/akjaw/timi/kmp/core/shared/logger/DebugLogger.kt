package com.akjaw.timi.kmp.core.shared.logger

interface DebugLogger {

    fun log(tag: String, message: String)

    fun error(tag: String, throwable: Throwable)
}
