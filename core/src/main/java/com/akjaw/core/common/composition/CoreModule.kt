package com.akjaw.core.common.composition

import com.akjaw.core.common.domain.ApplicationInitializer
import com.akjaw.core.common.domain.KlockTimestampProvider
import com.akjaw.core.common.domain.TimestampProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds

@Module(includes = [DispatchersModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class CoreModule {

    @Binds
    abstract fun bindTimestampProvider(klockTimestampProvider: KlockTimestampProvider): TimestampProvider

    @Multibinds
    abstract fun bindApplicationInitializer(): Set<ApplicationInitializer>
}
