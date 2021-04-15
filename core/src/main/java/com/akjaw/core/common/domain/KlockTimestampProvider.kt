package com.akjaw.core.common.domain

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.soywiz.klock.DateTime
import javax.inject.Inject

internal class KlockTimestampProvider @Inject constructor() : TimestampProvider {

    override fun getMilliseconds(): TimestampMilliseconds =
        DateTime.nowUnixLong().toTimestampMilliseconds()
}
