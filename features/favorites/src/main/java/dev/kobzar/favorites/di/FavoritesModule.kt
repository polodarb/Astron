package dev.kobzar.favorites.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.kobzar.favorites.FavoritesViewModel

@Module
@InstallIn(ActivityComponent::class)
interface FavoritesModule {

    @Binds
    @IntoMap
    @ScreenModelKey(FavoritesViewModel::class)
    fun bindsFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ScreenModel

}