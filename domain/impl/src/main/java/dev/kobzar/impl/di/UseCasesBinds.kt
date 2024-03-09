package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.domain.useCases.ConfigureFirstStart
import dev.kobzar.domain.useCases.reformatUnits.ReformatDiameterUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatMissDistanceUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatRelativeVelocityUseCase
import dev.kobzar.impl.useCases.ConfigureFirstStartImpl
import dev.kobzar.impl.useCases.reformatUnits.ReformatDiameterUseCaseImpl
import dev.kobzar.impl.useCases.reformatUnits.ReformatMissDistanceUseCaseImpl
import dev.kobzar.impl.useCases.reformatUnits.ReformatRelativeVelocityUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesBinds {

    @Binds
    @Singleton
    fun bindConfigureFirstStart(impl: ConfigureFirstStartImpl): ConfigureFirstStart

    @Binds
    @Singleton
    fun bindReformatDiameterUnit(impl: ReformatDiameterUseCaseImpl): ReformatDiameterUseCase

    @Binds
    @Singleton
    fun bindReformatMissDistance(impl: ReformatMissDistanceUseCaseImpl): ReformatMissDistanceUseCase

    @Binds
    @Singleton
    fun bindReformatRelativeVelocity(impl: ReformatRelativeVelocityUseCaseImpl): ReformatRelativeVelocityUseCase
}