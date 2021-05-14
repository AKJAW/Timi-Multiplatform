package com.akjaw.settings.data

import com.akjaw.settings.domain.SettingsOption

internal class InMemorySettingsRepository : Repository {

    private val mutableBooleanSettings = mutableMapOf<SettingsOption, Boolean>()

    override fun getBoolean(key: SettingsOption) = mutableBooleanSettings[key]

    override fun setBoolean(key: SettingsOption, value: Boolean) {
        mutableBooleanSettings[key] = value
    }
}
