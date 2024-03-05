package dev.kobzar.repository

import dev.kobzar.network.models.NetworkAsteroidsModel
import dev.kobzar.repository.models.MainAsteroidsModel

interface AsteroidsRepository {

    suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): MainAsteroidsModel?

}