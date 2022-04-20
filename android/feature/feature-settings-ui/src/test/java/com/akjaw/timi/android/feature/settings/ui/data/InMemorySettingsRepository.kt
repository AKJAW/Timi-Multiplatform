package com.akjaw.timi.android.feature.settings.ui.data

import com.akjaw.timi.android.feature.settings.ui.domain.BooleanSettingsOption

internal class InMemorySettingsRepository : SettingsRepository {

    private val mutableBooleanSettings = mutableMapOf<BooleanSettingsOption, Boolean>()

    override fun containsOption(option: BooleanSettingsOption): Boolean =
        mutableBooleanSettings.containsKey(option)

    override fun getBoolean(option: BooleanSettingsOption): Boolean =
        mutableBooleanSettings[option] ?: false

    override fun setBoolean(option: BooleanSettingsOption, value: Boolean) {
        mutableBooleanSettings[option] = value
    }
}
