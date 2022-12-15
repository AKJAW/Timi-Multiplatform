package com.akjaw.timi.android.feature.settings.ui.data

import com.akjaw.timi.android.feature.settings.ui.domain.BooleanSettingsOption

interface SettingsRepository {

    fun containsOption(option: BooleanSettingsOption): Boolean

    fun getBoolean(option: BooleanSettingsOption): Boolean

    fun setBoolean(option: BooleanSettingsOption, value: Boolean)
}
