package dev.kobzar.impl.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.service.NasaAPI
import okhttp3.Interceptor
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val API_KEY = "gHAEmoJC38y9u6oZUdOnaHf4OewOgMPFqd5piwqp"
    private const val BASE_URL: String = "https://api.nasa.gov/neo/rest/v1/neo/?api_key=$API_KEY"

    @Provides
    @Singleton
    @Named("AsterRetrofit")
    fun provideAsterRetrofit(retrofit: retrofit2.Retrofit): NasaAPI {
        return retrofit.create(NasaAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): retrofit2.Retrofit {
        return createRetrofit(BASE_URL, okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor(context))
            .build()
    }

    private fun createRetrofit(url: String, okHttpClient: OkHttpClient) = retrofit2.Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

    private fun createLoggingInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return ChuckerInterceptor(context)
    }

}