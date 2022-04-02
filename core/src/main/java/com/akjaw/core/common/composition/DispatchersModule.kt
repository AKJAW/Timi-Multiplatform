package com.akjaw.core.common.composition

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@DisableInstallInCheck
@Module
internal object DispatchersModule : KoinComponent {

    private val dispatcherProvider: DispatcherProvider by inject()

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = dispatcherProvider
}
