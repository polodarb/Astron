package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.AsteroidsRepositoryImpl
import dev.kobzar.impl.DataStoreRepositoryImpl
import dev.kobzar.repository.AsteroidsRepository
import dev.kobzar.repository.DataStoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinds {

    @Binds
    @Singleton
    fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    @Singleton
    fun bindAsteroidsRepository(impl: AsteroidsRepositoryImpl): AsteroidsRepository

}