package com.akjaw.timi.kmp.core.shared.logger

import co.touchlab.kermit.Logger

class KermitDebugLogger(
    private val logger: Logger
) : DebugLogger {

    override fun log(tag: String, message: String) {
        logger.withTag(tag).i(message)
    }

    override fun error(tag: String, throwable: Throwable) {
        logger.withTag(tag).e(throwable.stackTraceToString())
    }
}
