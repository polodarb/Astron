package dev.kobzar.impl

import dev.kobzar.network.source.NetworkSource
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.mappers.ModelsMapper.toMainDetailsModel
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AsteroidDetailsRepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource
): AsteroidDetailsRepository {

     override suspend fun getAsteroidDetails(asteroidId: String): Flow<UiState<MainDetailsModel>> = flow {
        runCatching {
            val data = networkSource.getAsteroidDetails(asteroidId)
            emit(UiState.Success(data.toMainDetailsModel()))
        }.onFailure {
            emit(UiState.Error(it))
        }

    }
}