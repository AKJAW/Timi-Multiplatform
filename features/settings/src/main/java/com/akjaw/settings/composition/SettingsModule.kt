package com.akjaw.settings.composition

import com.akjaw.settings.view.SettingsScreenCreator
import com.akjaw.settings.view.SettingsScreenCreatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsModule {

    @Binds
    abstract fun bindStopwatchScreenCreator(creator: SettingsScreenCreatorImpl): SettingsScreenCreator
}
