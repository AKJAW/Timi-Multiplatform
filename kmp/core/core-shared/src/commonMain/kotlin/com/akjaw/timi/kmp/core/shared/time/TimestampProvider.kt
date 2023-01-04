package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds

interface TimestampProvider {

    fun getMilliseconds(): TimestampMilliseconds
}
