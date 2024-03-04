package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.domain.useCases.ConfigureFirstStart
import dev.kobzar.impl.useCases.ConfigureFirstStartImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesBinds {

    @Binds
    @Singleton
    fun bindConfigureFirstStart(impl: ConfigureFirstStartImpl): ConfigureFirstStart

}