package com.akjaw.timicompose.composition

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimestampProviderStub @Inject constructor() : TimestampProvider {

    var currentMilliseconds: Long = 0

    override fun getMilliseconds(): TimestampMilliseconds =
        currentMilliseconds.toTimestampMilliseconds()
}
