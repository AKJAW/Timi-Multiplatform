package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.core.common.domain.model.TimestampMilliseconds

interface TimestampProvider {

    fun getMilliseconds(): TimestampMilliseconds
}
