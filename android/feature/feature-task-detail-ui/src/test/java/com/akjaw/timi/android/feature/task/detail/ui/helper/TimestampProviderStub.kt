package com.akjaw.timi.android.feature.task.detail.ui.helper

import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider

class TimestampProviderStub : TimestampProvider {

    var value: TimestampMilliseconds = 0.toTimestampMilliseconds()

    override fun getMilliseconds(): TimestampMilliseconds = value
}
