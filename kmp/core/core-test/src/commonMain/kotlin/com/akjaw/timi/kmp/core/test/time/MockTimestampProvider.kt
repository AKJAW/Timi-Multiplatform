package com.akjaw.timi.kmp.core.test.time

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds

class MockTimestampProvider : TimestampProvider {

    private var value: Long = 0

    fun givenTimePassed(amount: Long = 0) {
        value = amount
    }

    override fun getMilliseconds(): TimestampMilliseconds =
        value.toTimestampMilliseconds()
}
