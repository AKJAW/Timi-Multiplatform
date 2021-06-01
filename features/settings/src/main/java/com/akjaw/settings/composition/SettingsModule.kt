package com.akjaw.settings.composition

import android.content.Context
import android.content.SharedPreferences
import com.akjaw.core.common.data.persistance.SharedPreferencesKeys
import com.akjaw.core.common.domain.ApplicationInitializer
import com.akjaw.settings.data.ResourcesSystemDarkModeProvider
import com.akjaw.settings.data.SettingsRepository
import com.akjaw.settings.data.SharedPreferencesSettingsRepository
import com.akjaw.settings.data.SystemDarkModeProvider
import com.akjaw.settings.domain.DarkThemeInitializer
import com.akjaw.settings.view.SettingsScreenCreator
import com.akjaw.settings.view.SettingsScreenCreatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: SettingsScreenCreatorImpl): SettingsScreenCreator

    @Binds
    abstract fun bindSettingsRepository(repository: SharedPreferencesSettingsRepository): SettingsRepository

    @Binds
    abstract fun bindSystemDarkModeProvider(provider: ResourcesSystemDarkModeProvider): SystemDarkModeProvider

    @Binds
    @IntoSet
    abstract fun bindDarkThemeInitializer(initializer: DarkThemeInitializer): ApplicationInitializer

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
