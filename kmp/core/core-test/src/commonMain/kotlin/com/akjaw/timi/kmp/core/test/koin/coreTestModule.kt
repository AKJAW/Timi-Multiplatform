package com.akjaw.timi.kmp.core.test.koin

import com.akjaw.timi.kmp.core.shared.logger.DebugLogger
import com.akjaw.timi.kmp.core.test.logger.TestDebugLogger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreTestModule = module {
    singleOf(::TestDebugLogger) bind DebugLogger::class
}