package dev.kobzar.impl.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.AppDatabase
import dev.kobzar.impl.dao.AsteroidDetailsDao
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "nextgen_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAsteroidDetailsDao(
        database: AppDatabase
    ): AsteroidDetailsDao {
        return database.asteroidDetailsDao()
    }

}
