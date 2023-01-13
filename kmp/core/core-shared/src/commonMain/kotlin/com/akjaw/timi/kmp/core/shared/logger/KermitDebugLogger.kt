package com.akjaw.timi.kmp.core.shared.logger

import co.touchlab.kermit.Logger

internal class KermitDebugLogger : DebugLogger {

    override fun log(tag: String, message: String) {
        Logger.withTag(tag).i(message)
    }

    override fun error(tag: String, throwable: Throwable) {
        Logger.withTag(tag).e(throwable.stackTraceToString())
    }
}
