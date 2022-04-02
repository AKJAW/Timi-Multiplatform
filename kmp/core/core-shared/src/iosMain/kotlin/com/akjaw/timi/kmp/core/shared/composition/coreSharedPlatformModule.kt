package com.akjaw.timi.kmp.core.shared.composition

import com.akjaw.timi.kmp.core.shared.coroutines.IosDispatcherProvider
import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

actual fun Module.coreSharedPlatformModule() {
    single<DispatcherProvider> { IosDispatcherProvider() }
}
