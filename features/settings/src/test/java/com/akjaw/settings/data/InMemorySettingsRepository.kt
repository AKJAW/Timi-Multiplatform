package com.akjaw.settings.data

import com.akjaw.settings.domain.BooleanSettingsOption

internal class InMemorySettingsRepository : SettingsRepository {

    private val mutableBooleanSettings = mutableMapOf<BooleanSettingsOption, Boolean>()

    override fun getBoolean(option: BooleanSettingsOption): Boolean =
        mutableBooleanSettings[option] ?: false

    override fun setBoolean(option: BooleanSettingsOption, value: Boolean) {
        mutableBooleanSettings[option] = value
    }
}
