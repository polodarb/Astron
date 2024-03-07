package dev.kobzar.details.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelFactory
import cafe.adriel.voyager.hilt.ScreenModelFactoryKey
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.kobzar.details.DetailsViewModel

@Module
@InstallIn(ActivityComponent::class)
abstract class DetailsModule {

    @Binds
    @IntoMap
    @ScreenModelKey(DetailsViewModel::class)
    abstract fun bindDetailsScreenModel(detailsViewModel: DetailsViewModel): ScreenModel

//    @Binds
//    @IntoMap
//    @ScreenModelFactoryKey(DetailsViewModel.Factory::class)
//    abstract fun bindDetailsScreenModelFactory(
//        hiltDetailsScreenModelFactory: DetailsViewModel.Factory
//    ): ScreenModelFactory

}