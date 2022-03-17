package com.akjaw.core.common.composition

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Module
@InstallIn(SingletonComponent::class)
object TimestampModule: KoinComponent {

    @Provides
    internal fun provideTimestampProvider(): TimestampProvider = get()
}
