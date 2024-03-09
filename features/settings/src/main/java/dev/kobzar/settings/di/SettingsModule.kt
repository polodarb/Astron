package dev.kobzar.settings.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.kobzar.settings.SettingsViewModel

@Module
@InstallIn(ActivityComponent::class)
abstract class SettingsModule {

    @Binds
    @IntoMap
    @ScreenModelKey(SettingsViewModel::class)
    abstract fun bindSettingsScreenModel(settingsViewModel: SettingsViewModel): ScreenModel

}