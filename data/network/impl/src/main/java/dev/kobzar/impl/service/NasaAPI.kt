package dev.kobzar.impl.service

import dev.kobzar.network.models.NetworkAsteroidsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "gHAEmoJC38y9u6oZUdOnaHf4OewOgMPFqd5piwqp"

interface NasaAPI {

    @GET("feed")
    suspend fun getAsteroidsByDate(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): NetworkAsteroidsModel

}