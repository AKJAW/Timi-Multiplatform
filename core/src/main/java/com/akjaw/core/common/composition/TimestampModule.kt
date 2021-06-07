package com.akjaw.core.common.composition

import com.akjaw.core.common.domain.KlockTimestampProvider
import com.akjaw.core.common.domain.TimestampProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TimestampModule {

    @Binds
    internal abstract fun bindTimestampProvider(
        klockTimestampProvider: KlockTimestampProvider
    ): TimestampProvider
}
