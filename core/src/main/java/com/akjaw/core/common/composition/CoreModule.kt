package com.akjaw.core.common.composition

import com.akjaw.core.common.domain.ActivityInitializer
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds

@Module(includes = [DispatchersModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class CoreModule {

    @Multibinds
    abstract fun bindActivityInitializer(): Set<ActivityInitializer>
}
