package com.akjaw.settings.data

import com.akjaw.settings.domain.BooleanSettingsOption

internal class InMemorySettingsRepository : Repository {

    private val mutableBooleanSettings = mutableMapOf<BooleanSettingsOption, Boolean>()

    override fun getBoolean(key: BooleanSettingsOption) = mutableBooleanSettings[key]

    override fun setBoolean(key: BooleanSettingsOption, value: Boolean) {
        mutableBooleanSettings[key] = value
    }
}
