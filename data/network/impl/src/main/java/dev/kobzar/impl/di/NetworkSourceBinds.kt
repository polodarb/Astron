package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.source.NetworkSourceImpl
import dev.kobzar.network.source.NetworkSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkSourceBinds {

    @Binds
    @Singleton
    fun bindNetworkSource(impl: NetworkSourceImpl): NetworkSource

}