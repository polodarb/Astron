package dev.kobzar.onboarding.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.kobzar.onboarding.OnBoardingViewModel

@Module
@InstallIn(ActivityComponent::class)
abstract class OnBoardingModule {

    @Binds
    @IntoMap
    @ScreenModelKey(OnBoardingViewModel::class)
    abstract fun bindOnBoardingViewModel(onBoardingViewModel: OnBoardingViewModel): ScreenModel

}
