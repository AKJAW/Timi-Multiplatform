package com.akjaw.settings.data

import com.akjaw.settings.domain.BooleanSettingsOption

interface Repository {

    fun getBoolean(option: BooleanSettingsOption): Boolean?

    fun setBoolean(option: BooleanSettingsOption, value: Boolean)
}
