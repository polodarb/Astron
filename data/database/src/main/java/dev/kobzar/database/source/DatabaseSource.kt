package dev.kobzar.database.source

import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {

    suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData)

    suspend fun getAsteroidDetails(asteroidId: String): Flow<MainDetailsWithCloseApproachData>

    suspend fun getItemsCount(): Flow<Int>

    suspend fun getAllAsteroidDetails(): Flow<List<MainDetailsWithCloseApproachData>>

    suspend fun isAsteroidDetailsExists(asteroidId: String): Flow<Boolean>

    suspend fun deleteAsteroidDetails(asteroidId: String)

}