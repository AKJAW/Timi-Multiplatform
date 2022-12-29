package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds
import com.soywiz.klock.DateTime

internal class KlockTimestampProvider : TimestampProvider {

    override fun getMilliseconds(): TimestampMilliseconds =
        DateTime.nowUnixLong().toTimestampMilliseconds()
}
