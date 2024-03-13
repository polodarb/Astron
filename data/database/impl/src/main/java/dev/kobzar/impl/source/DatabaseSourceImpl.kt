package dev.kobzar.impl.source

import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.database.entities.NotifiedAsteroidsEntity
import dev.kobzar.database.source.DatabaseSource
import dev.kobzar.impl.dao.AsteroidsDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseSourceImpl @Inject constructor(
    private val asteroidsDatabaseDao: AsteroidsDatabaseDao
): DatabaseSource {

    override suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData) {
        withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.insertAsteroidDetails(mainDetails)
        }
    }

    override suspend fun getAsteroidDetails(asteroidId: String): Flow<MainDetailsWithCloseApproachData> {
        return withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.getAsteroidDetails(asteroidId)
        }
    }

    override suspend fun getItemsCount(): Flow<Int> {
        return withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.getItemsCount()
        }
    }

    override suspend fun getAllAsteroidDetails(): Flow<List<MainDetailsWithCloseApproachData>> {
        return withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.getAllAsteroidDetails()
        }
    }

    override suspend fun isAsteroidDetailsExists(asteroidId: String): Flow<Boolean> {
        return withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.isAsteroidDetailsExists(asteroidId)
        }
    }

    override suspend fun deleteAsteroidDetails(asteroidId: String) {
        withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.deleteAsteroidDetails(asteroidId)
        }
    }

    override suspend fun getNotifiedAsteroids(): Flow<List<NotifiedAsteroidsEntity>> {
        return withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.getNotifiedAsteroids()
        }
    }

    override suspend fun insertNotifiedAsteroids(notifiedAsteroids: NotifiedAsteroidsEntity) {
        withContext(Dispatchers.IO) {
            asteroidsDatabaseDao.insertNotifiedAsteroids(notifiedAsteroids)
        }
    }
}