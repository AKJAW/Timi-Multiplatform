package com.akjaw.core.common.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@DisableInstallInCheck
@Module
internal object DispatchersModule {

    @Provides
    @BackgroundDispatcherQualifier
    fun provideBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcherQualifier
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
