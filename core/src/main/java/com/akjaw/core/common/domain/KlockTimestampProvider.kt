package com.akjaw.core.common.domain

import com.soywiz.klock.DateTime
import javax.inject.Inject

internal class KlockTimestampProvider @Inject constructor() : TimestampProvider {

    override fun getMilliseconds(): Long =
        DateTime.nowUnixLong()
}
