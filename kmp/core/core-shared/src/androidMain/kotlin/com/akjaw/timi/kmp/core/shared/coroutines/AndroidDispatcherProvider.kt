package com.akjaw.timi.kmp.core.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AndroidDispatcherProvider : DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
