package dev.kobzar.repository

import dev.kobzar.model.models.MainAsteroidsModel

interface AsteroidsRepository {

    suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): MainAsteroidsModel?

    suspend fun getAsteroidsByDate(
        url: String
    ): MainAsteroidsModel

}