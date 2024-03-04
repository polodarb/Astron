package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.datasource.DataStoreSource
import dev.kobzar.impl.DataStoreSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceBinds {

    @Binds
    @Singleton
    fun bindDataStoreSource(impl: DataStoreSourceImpl): DataStoreSource

}