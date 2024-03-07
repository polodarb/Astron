package dev.kobzar.repository

import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow

interface AsteroidDetailsRepository {

    suspend fun getAsteroidDetails(asteroidId: String): Flow<UiState<MainDetailsModel>>

}