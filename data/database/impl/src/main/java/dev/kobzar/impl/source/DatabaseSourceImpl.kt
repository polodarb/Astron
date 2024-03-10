package dev.kobzar.impl.source

import dev.kobzar.database.entities.MainDetailsEntity
import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.database.source.DatabaseSource
import dev.kobzar.impl.dao.AsteroidDetailsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DatabaseSourceImpl @Inject constructor(
    private val asteroidDetailsDao: AsteroidDetailsDao
): DatabaseSource {

    override suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData) {
        withContext(Dispatchers.IO) {
            asteroidDetailsDao.insertAsteroidDetails(mainDetails)
        }
    }

    override suspend fun getAsteroidDetails(asteroidId: String): Flow<MainDetailsWithCloseApproachData> {
        return withContext(Dispatchers.IO) {
            asteroidDetailsDao.getAsteroidDetails(asteroidId)
        }
    }

    override suspend fun getAllAsteroidDetails(): Flow<List<MainDetailsWithCloseApproachData>> {
        return withContext(Dispatchers.IO) {
            asteroidDetailsDao.getAllAsteroidDetails()
        }
    }

    override suspend fun deleteAsteroidDetails(asteroidId: String) {
        withContext(Dispatchers.IO) {
            asteroidDetailsDao.deleteAsteroidDetails(asteroidId)
        }
    }
}