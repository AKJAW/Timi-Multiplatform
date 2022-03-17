package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.soywiz.klock.DateTime

internal class KlockTimestampProvider : TimestampProvider {

    override fun getMilliseconds(): TimestampMilliseconds =
        DateTime.nowUnixLong().toTimestampMilliseconds()
}
