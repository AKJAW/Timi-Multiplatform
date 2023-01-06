package com.akjaw.timi.kmp.core.shared.logger

// TODO propagate to the rest of the codebase + remove other Kermit logic infrastructure
interface DebugLogger {

    fun log(tag: String, message: String)

    fun error(tag: String, throwable: Throwable)
}
