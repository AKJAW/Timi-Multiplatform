package com.akjaw.settings.composition

import android.content.Context
import android.content.SharedPreferences
import com.akjaw.core.common.data.persistance.SharedPreferencesKeys
import com.akjaw.settings.data.SettingsRepository
import com.akjaw.settings.data.SharedPreferencesSettingsRepository
import com.akjaw.settings.view.SettingsScreenCreator
import com.akjaw.settings.view.SettingsScreenCreatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: SettingsScreenCreatorImpl): SettingsScreenCreator

    @Binds
    abstract fun bindSettingsRepository(settingsRepository: SharedPreferencesSettingsRepository): SettingsRepository

    companion object {

        @Provides
        fun bindSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(
                SharedPreferencesKeys.settings,
                Context.MODE_PRIVATE
            )
        }
    }
}
