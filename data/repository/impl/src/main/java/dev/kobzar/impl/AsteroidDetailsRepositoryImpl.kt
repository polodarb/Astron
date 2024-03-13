package dev.kobzar.impl

import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.database.source.DatabaseSource
import dev.kobzar.model.mappers.DatabaseMapper.toListMainDetailsModel
import dev.kobzar.model.mappers.DatabaseMapper.toListMainNotifiedModel
import dev.kobzar.model.mappers.DatabaseMapper.toMainDetailsModel
import dev.kobzar.model.mappers.DatabaseMapper.toNotifiedAsteroidsEntity
import dev.kobzar.model.mappers.NetworkMapper.toMainDetailsModel
import dev.kobzar.network.source.NetworkSource
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AsteroidDetailsRepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource,
    private val databaseSource: DatabaseSource
) : AsteroidDetailsRepository {

    override suspend fun getAsteroidDetails(asteroidId: String): Flow<UiState<dev.kobzar.model.models.MainDetailsModel>> =
        channelFlow {
            runCatching {
                val data = networkSource.getAsteroidDetails(asteroidId)
                send(UiState.Success(data.toMainDetailsModel()))
            }.onFailure { throwable ->
                databaseSource.isAsteroidDetailsExists(asteroidId).collect { isExists ->
                    if (isExists) {
                        runCatching {
                            databaseSource.getAsteroidDetails(asteroidId).collect {
                                send(UiState.Success(it.toMainDetailsModel()))
                            }
                        }.onFailure {
                            send(UiState.Error(it))
                        }
                    } else {
                        send(UiState.Error(throwable))
                    }
                }
            }
        }

    override suspend fun getAllAsteroidDetails(): Flow<UiState<List<dev.kobzar.model.models.MainDetailsModel>>> = channelFlow {
        runCatching {
            databaseSource.getAllAsteroidDetails().collect {
                send(UiState.Success(it.toListMainDetailsModel()))
            }
        }.onFailure {
            send(UiState.Error(it))
        }
    }

    override suspend fun getItemsCount(): Flow<Int> {
        return databaseSource.getItemsCount()
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

    override suspend fun getNotifiedAsteroids(): Flow<List<dev.kobzar.model.models.MainNotifiedModel>> = flow{
        databaseSource.getNotifiedAsteroids().collect {
            emit(it.toListMainNotifiedModel())
        }
    }

    override suspend fun insertNotifiedAsteroids(notifiedAsteroids: dev.kobzar.model.models.MainNotifiedModel) {
        databaseSource.insertNotifiedAsteroids(notifiedAsteroids.toNotifiedAsteroidsEntity())
    }
}