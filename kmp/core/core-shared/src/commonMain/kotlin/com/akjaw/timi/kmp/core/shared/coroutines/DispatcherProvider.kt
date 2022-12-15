package com.akjaw.timi.kmp.core.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher

    val default: CoroutineDispatcher

    val io: CoroutineDispatcher
}
