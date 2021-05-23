package com.akjaw.settings.data

import com.akjaw.settings.domain.BooleanSettingsOption
import javax.inject.Inject

internal class InitialSettingsOptionsProvider @Inject constructor(
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
