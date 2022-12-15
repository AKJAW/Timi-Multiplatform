package com.akjaw.timi.android.feature.settings.ui.data

import com.akjaw.timi.android.feature.settings.ui.domain.BooleanSettingsOption

internal class InitialSettingsOptionsProvider(
    private val settingsRepository: SettingsRepository
) {

    private val options = mapOf(
        createOption(BooleanSettingsOption.DARK_MODE)
    )

    fun get(): Map<BooleanSettingsOption, Boolean> = options

    private fun createOption(
        booleanSettingsOption: BooleanSettingsOption
    ): Pair<BooleanSettingsOption, Boolean> {
        return booleanSettingsOption to settingsRepository.getBoolean(booleanSettingsOption)
    }
}
