package dev.kobzar.impl

import dev.kobzar.database.source.DatabaseSource
import dev.kobzar.model.mappers.NetworkMapper.toMainAsteroidsModel
import dev.kobzar.network.source.NetworkSource
import dev.kobzar.repository.AsteroidsRepository
import javax.inject.Inject

class AsteroidsRepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource,
    private val databaseSource: DatabaseSource
): AsteroidsRepository {

    override suspend fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): dev.kobzar.model.models.MainAsteroidsModel {
        val networkAsteroidsModel = networkSource.getAsteroidsByDate(startDate, endDate)
        return networkAsteroidsModel.toMainAsteroidsModel()
    }

    override suspend fun getAsteroidsByDate(url: String): dev.kobzar.model.models.MainAsteroidsModel {
        return networkSource.getAsteroidsByDate(url).toMainAsteroidsModel()
    }

}