package com.akjaw.settings.data

import android.content.SharedPreferences
import com.akjaw.settings.domain.BooleanSettingsOption

internal class SharedPreferencesSettingsRepository (
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    override fun containsOption(option: BooleanSettingsOption): Boolean =
        sharedPreferences.contains(option.key)

    override fun getBoolean(option: BooleanSettingsOption): Boolean =
        sharedPreferences.getBoolean(option.key, false)

    override fun setBoolean(option: BooleanSettingsOption, value: Boolean) {
        sharedPreferences.edit().putBoolean(option.key, value).apply()
    }
}
