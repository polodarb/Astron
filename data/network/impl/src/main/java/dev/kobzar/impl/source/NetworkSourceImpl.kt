package dev.kobzar.impl.source

import dev.kobzar.impl.service.NasaAPI
import dev.kobzar.network.base.BaseNetworkSource
import dev.kobzar.network.models.NetworkAsteroidsModel
import dev.kobzar.network.source.NetworkSource
import javax.inject.Inject
import javax.inject.Named

class NetworkSourceImpl @Inject constructor(
    @Named("AsterRetrofit") private val api: NasaAPI
): NetworkSource {

    override suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): NetworkAsteroidsModel = apiCall { api.getAsteroidsByDate(startDate, endDate) }

}