package com.akjaw.settings.data

import com.akjaw.settings.domain.BooleanSettingsOption

interface Repository {

    fun getBoolean(key: BooleanSettingsOption): Boolean?

    fun setBoolean(key: BooleanSettingsOption, value: Boolean)
}
