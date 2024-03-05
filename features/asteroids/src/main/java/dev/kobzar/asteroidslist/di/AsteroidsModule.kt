package dev.kobzar.asteroidslist.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.kobzar.asteroidslist.AsteroidsListViewModel

@Module
@InstallIn(ActivityComponent::class)
abstract class AsteroidsModule {

    @Binds
    @IntoMap
    @ScreenModelKey(AsteroidsListViewModel::class)
    abstract fun bindAsteroidsListViewModel(onBoardingViewModel: AsteroidsListViewModel): ScreenModel

}