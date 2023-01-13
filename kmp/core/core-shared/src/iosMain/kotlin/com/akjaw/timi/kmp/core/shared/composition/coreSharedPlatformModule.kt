package com.akjaw.timi.kmp.core.shared.composition

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.core.shared.coroutines.IosDispatcherProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

actual fun Module.coreSharedPlatformModule() {
    singleOf(::IosDispatcherProvider) bind DispatcherProvider::class
}
