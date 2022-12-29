package com.akjaw.timi.kmp.core.test.time

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds

class StubTimestampProvider : TimestampProvider {

    private var value: TimestampMilliseconds = 0.toTimestampMilliseconds()

    fun setValue(value: Long) {
        this.value = value.toTimestampMilliseconds()
    }

    override fun getMilliseconds(): TimestampMilliseconds = value
}
