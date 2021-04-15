package com.akjaw.core.common.domain

import com.akjaw.core.common.domain.model.TimestampMilliseconds

interface TimestampProvider {

    fun getMilliseconds(): TimestampMilliseconds
}
