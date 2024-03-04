package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.DataStoreManagerImpl
import dev.kobzar.preferences.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    @Singleton
    fun bindDataStoreManager(dataStoreManager: DataStoreManagerImpl): DataStoreManager

}