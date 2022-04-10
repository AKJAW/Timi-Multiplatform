package com.akjaw.timi.kmp.core.shared.composition

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.core.shared.coroutines.IosDispatcherProvider
import org.koin.core.module.Module

actual fun Module.coreSharedPlatformModule() {
    single<DispatcherProvider> { IosDispatcherProvider() }
}
