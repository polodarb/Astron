package dev.kobzar.repository

import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.model.models.MainDetailsModel
import dev.kobzar.model.models.MainNotifiedModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow

interface AsteroidDetailsRepository {

    suspend fun getAsteroidDetails(asteroidId: String): Flow<UiState<MainDetailsModel>>

    suspend fun getAllAsteroidDetails(): Flow<UiState<List<MainDetailsModel>>>

    suspend fun getItemsCount(): Flow<Int>

    suspend fun isAsteroidDetailsExists(asteroidId: String): Flow<Boolean>

    suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData)

    suspend fun deleteAsteroidDetails(asteroidId: String)

    suspend fun getNotifiedAsteroids(): Flow<List<MainNotifiedModel>>

    suspend fun insertNotifiedAsteroids(notifiedAsteroids: MainNotifiedModel)

}