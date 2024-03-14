package dev.kobzar.network.source

import dev.kobzar.network.base.BaseNetworkSource
import dev.kobzar.network.models.NetworkAsteroid
import dev.kobzar.network.models.NetworkAsteroidsModel

interface NetworkSource: BaseNetworkSource {

    suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): NetworkAsteroidsModel

    suspend fun getAsteroidsByDate(
        url: String
    ): NetworkAsteroidsModel

    suspend fun getAsteroidDetails(
        asteroidId: String
    ): NetworkAsteroid

}