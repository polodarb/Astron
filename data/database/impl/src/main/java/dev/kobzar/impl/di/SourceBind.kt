package dev.kobzar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.database.source.DatabaseSource
import dev.kobzar.impl.source.DatabaseSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SourceBind {

    @Binds
    @Singleton
    fun bindDatabaseSource(impl: DatabaseSourceImpl): DatabaseSource

}