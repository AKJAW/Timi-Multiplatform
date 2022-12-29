package com.akjaw.timi.kmp.feature.task.dependency.detail

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds

// TODO remove and use the one from core-test
class TimestampProviderStub : TimestampProvider {

    var value: TimestampMilliseconds = 0.toTimestampMilliseconds()

    override fun getMilliseconds(): TimestampMilliseconds = value
}
