package com.akjaw.timicompose.composition

import com.akjaw.core.common.composition.TimestampModule
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TimestampModule::class],
)
abstract class TestTimestampModule {

    @Binds
    abstract fun bindTimestampProvider(provider: TimestampProviderStub): TimestampProvider
}

@Singleton
class TimestampProviderStub @Inject constructor() : TimestampProvider {

    var currentMilliseconds: Long = 0

    override fun getMilliseconds(): TimestampMilliseconds =
        currentMilliseconds.toTimestampMilliseconds()
}
