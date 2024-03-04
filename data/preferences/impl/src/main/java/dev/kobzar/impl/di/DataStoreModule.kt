package dev.kobzar.impl.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.DataStoreManagerImpl
import dev.kobzar.preferences.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManagerImpl(context)
    }

//    @Provides
//    @Singleton
//    fun provideContext(application: Application): Context {
//        return application.applicationContext
//    }
}