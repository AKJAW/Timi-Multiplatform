package com.akjaw.timi.kmp.feature.stopwatch.domain.helpers

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider

// TODO move out to core-test?
class FakeTimestampProvider : TimestampProvider {

    private var value: Long = 0

    fun givenTimePassed(amount: Long = 0) {
        value = amount
    }

    override fun getMilliseconds(): TimestampMilliseconds =
        value.toTimestampMilliseconds()
}