package dev.kobzar.compare.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.kobzar.compare.CompareViewModel

@Module
@InstallIn(ActivityComponent::class)
interface CompareModule {

    @Binds
    @IntoMap
    @ScreenModelKey(CompareViewModel::class)
    fun bindCompareViewModel(compareViewModel: CompareViewModel): ScreenModel

}