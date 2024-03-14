package dev.kobzar.impl.service

import dev.kobzar.impl.di.RetrofitModule.BASE_URL
import dev.kobzar.network.models.NetworkAsteroid
import dev.kobzar.network.models.NetworkAsteroidsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

private const val API_KEY = "gHAEmoJC38y9u6oZUdOnaHf4OewOgMPFqd5piwqp"

interface NasaAPI {

    @GET("${BASE_URL}feed")
    suspend fun getAsteroidsByDate(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): NetworkAsteroidsModel

    @GET
    suspend fun getAsteroidsByDate(
        @Url url: String
    ): NetworkAsteroidsModel

    @GET("${BASE_URL}neo/{asteroidId}")
    suspend fun getAsteroidDetails(
        @Path("asteroidId") asteroidId: String,
        @Query("api_key") apiKey: String = API_KEY
    ): NetworkAsteroid

}