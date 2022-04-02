package com.akjaw.timi.kmp.core.shared.composition

import com.akjaw.timi.kmp.core.shared.coroutines.AndroidDispatcherProvider
import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.core.shared.time.KlockTimestampProvider
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

actual fun Module.coreSharedPlatformModule() {
    single<DispatcherProvider> { AndroidDispatcherProvider() }
}
