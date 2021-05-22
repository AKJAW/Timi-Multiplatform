package com.akjaw.settings.data

import android.content.SharedPreferences
import com.akjaw.settings.domain.BooleanSettingsOption
import javax.inject.Inject

internal class SharedPreferencesSettingsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Repository {

    override fun getBoolean(option: BooleanSettingsOption): Boolean =
        sharedPreferences.getBoolean(option.key, false)

    override fun setBoolean(option: BooleanSettingsOption, value: Boolean) {
        sharedPreferences.edit().putBoolean(option.key, value).apply()
    }
}
