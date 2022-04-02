package com.akjaw.timi.kmp.core.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class IosDispatcherProvider: DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    // TODO create a dispatcher with a big amount of threads?
    override val io: CoroutineDispatcher
        get() = Dispatchers.Default
}
