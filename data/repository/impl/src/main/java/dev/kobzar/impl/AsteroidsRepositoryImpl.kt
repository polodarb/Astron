package dev.kobzar.impl

import android.util.Log
import dev.kobzar.network.models.NetworkAsteroidsModel
import dev.kobzar.network.source.NetworkSource
import dev.kobzar.repository.AsteroidsRepository
import dev.kobzar.repository.mappers.ModelsMapper.toMainAsteroidsModel
import dev.kobzar.repository.models.MainAsteroidsModel
import javax.inject.Inject

class AsteroidsRepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource
): AsteroidsRepository {

    override suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): MainAsteroidsModel {
        val networkAsteroidsModel = networkSource.getAsteroidsByDate(startDate, endDate)
        return networkAsteroidsModel.toMainAsteroidsModel()
    }

    override suspend fun getAsteroidsByDate(url: String): MainAsteroidsModel {
        return networkSource.getAsteroidsByDate(url).toMainAsteroidsModel()
    }
}