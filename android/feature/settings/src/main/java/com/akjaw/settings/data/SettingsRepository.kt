package com.akjaw.settings.data

import com.akjaw.settings.domain.BooleanSettingsOption

interface SettingsRepository {

    fun containsOption(option: BooleanSettingsOption): Boolean

    fun getBoolean(option: BooleanSettingsOption): Boolean

    fun setBoolean(option: BooleanSettingsOption, value: Boolean)
}
