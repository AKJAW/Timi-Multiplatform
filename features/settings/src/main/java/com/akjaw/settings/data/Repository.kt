package com.akjaw.settings.data

import com.akjaw.settings.domain.SettingsOption

interface Repository {

    fun getBoolean(key: SettingsOption): Boolean?

    fun setBoolean(key: SettingsOption, value: Boolean)
}
