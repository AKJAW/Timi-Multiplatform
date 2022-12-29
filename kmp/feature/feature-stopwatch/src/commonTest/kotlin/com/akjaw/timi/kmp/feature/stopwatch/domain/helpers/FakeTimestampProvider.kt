package com.akjaw.timi.kmp.feature.stopwatch.domain.helpers

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds

// TODO move out to core-test?
class FakeTimestampProvider : TimestampProvider {

    private var value: Long = 0

    fun givenTimePassed(amount: Long = 0) {
        value = amount
    }

    override fun getMilliseconds(): TimestampMilliseconds =
        value.toTimestampMilliseconds()
}
