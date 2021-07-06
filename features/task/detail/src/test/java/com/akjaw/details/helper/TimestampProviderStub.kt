package com.akjaw.details.helper

import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds

class TimestampProviderStub : TimestampProvider {

    var value: TimestampMilliseconds = 0.toTimestampMilliseconds()

    override fun getMilliseconds(): TimestampMilliseconds = value
}
