package dev.kobzar.network.source

import dev.kobzar.network.base.BaseNetworkSource
import dev.kobzar.network.models.NetworkAsteroidsModel

interface NetworkSource: BaseNetworkSource {

    suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): NetworkAsteroidsModel

}