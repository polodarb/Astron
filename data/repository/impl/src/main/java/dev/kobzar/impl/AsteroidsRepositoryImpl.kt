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
        Log.d("TAG", "getAsteroidsByDate: $networkAsteroidsModel")
        return networkAsteroidsModel.toMainAsteroidsModel()
    }

}