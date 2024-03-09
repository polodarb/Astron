package dev.kobzar.impl.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kobzar.impl.service.NasaAPI
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

    @Provides
    @Singleton
    @Named("AsterRetrofit")
    fun provideAsterRetrofit(retrofit: Retrofit): NasaAPI {
        return retrofit.create(NasaAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return createRetrofit(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(KtConverterFactory(json = Json { ignoreUnknownKeys = true }))

    class KtConverterFactory(private val json: Json = Json) : Factory() {
        private fun getStrategy(type: Type) = Json.serializersModule.serializer(type)

        override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<out Annotation>,
            methodAnnotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<*, RequestBody> {
            return KtRequestBodyConverter(serializer = getStrategy(type = type))
        }

        private inner class KtRequestBodyConverter<T>(
            private val serializer: SerializationStrategy<T>
        ) : Converter<T, RequestBody> {
            override fun convert(value: T): RequestBody {
                return json.encodeToString(serializer, value).toRequestBody()
            }
        }

        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *> {
            return KtResponseBodyConverter(deserializer = getStrategy(type = type))
        }

        private inner class KtResponseBodyConverter<T>(
            private val deserializer: DeserializationStrategy<T>
        ) : Converter<ResponseBody, T> {
            override fun convert(value: ResponseBody): T {
                return json.decodeFromString(deserializer, value.string())
            }
        }
    }

    private fun createLoggingInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }
}
