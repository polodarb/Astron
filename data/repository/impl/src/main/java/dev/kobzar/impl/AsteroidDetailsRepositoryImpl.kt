package dev.kobzar.impl

import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.database.source.DatabaseSource
import dev.kobzar.network.source.NetworkSource
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.mappers.DatabaseMapper.toListMainDetailsModel
import dev.kobzar.repository.mappers.DatabaseMapper.toMainDetailsModel
import dev.kobzar.repository.mappers.NetworkMapper.toMainDetailsModel
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AsteroidDetailsRepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource,
    private val databaseSource: DatabaseSource
) : AsteroidDetailsRepository {

    override suspend fun getAsteroidDetails(asteroidId: String): Flow<UiState<MainDetailsModel>> =
        flow {
            runCatching {
                val data = networkSource.getAsteroidDetails(asteroidId)
                emit(UiState.Success(data.toMainDetailsModel()))
            }.onFailure { throwable ->
                databaseSource.isAsteroidDetailsExists(asteroidId).collect { isExists ->
                    if (isExists) {
                        runCatching {
                            databaseSource.getAsteroidDetails(asteroidId).collect {
                                emit(UiState.Success(it.toMainDetailsModel()))
                            }
                        }.onFailure {
                            emit(UiState.Error(it))
                        }
                    } else {
                        emit(UiState.Error(throwable))
                    }
                }
            }
        }

    override suspend fun getAllAsteroidDetails(): Flow<UiState<List<MainDetailsModel>>> = flow {
        runCatching {
            databaseSource.getAllAsteroidDetails().collect {
                emit(UiState.Success(it.toListMainDetailsModel()))
            }
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    override suspend fun isAsteroidDetailsExists(asteroidId: String): Flow<Boolean> {
        return databaseSource.isAsteroidDetailsExists(asteroidId)
    }

    override suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData) {
        databaseSource.insertAsteroidDetails(mainDetails)
    }

    override suspend fun deleteAsteroidDetails(asteroidId: String) {
        databaseSource.deleteAsteroidDetails(asteroidId)
    }
}