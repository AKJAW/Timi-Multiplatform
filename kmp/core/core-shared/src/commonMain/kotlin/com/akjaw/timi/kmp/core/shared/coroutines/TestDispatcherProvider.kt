package com.akjaw.timi.kmp.core.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProvider(private val testDispatcher: CoroutineDispatcher) : DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher

    override val io: CoroutineDispatcher
        get() = testDispatcher
}
