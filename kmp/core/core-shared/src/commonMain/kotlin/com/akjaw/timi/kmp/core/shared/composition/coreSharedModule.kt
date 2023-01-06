package com.akjaw.timi.kmp.core.shared.composition

import com.akjaw.timi.kmp.core.shared.logger.DebugLogger
import com.akjaw.timi.kmp.core.shared.logger.KermitDebugLogger
import com.akjaw.timi.kmp.core.shared.time.KlockTimestampProvider
import com.akjaw.timi.kmp.core.shared.time.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreSharedModule = module {
    factoryOf(::KlockTimestampProvider) bind TimestampProvider::class
    factoryOf(::TimestampMillisecondsFormatter)
    singleOf(::KermitDebugLogger) bind DebugLogger::class
    coreSharedPlatformModule()
}
