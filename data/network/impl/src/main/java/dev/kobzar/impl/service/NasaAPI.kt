package dev.kobzar.impl.service

import dev.kobzar.network.models.NetworkAsteroidsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaAPI {

    @GET("start_date")
    suspend fun getAsteroidsByDate(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<NetworkAsteroidsModel>

}