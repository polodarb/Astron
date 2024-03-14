package dev.kobzar.impl.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.kobzar.database.entities.CloseApproachDataEntity
import dev.kobzar.database.entities.MainDetailsEntity
import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.database.entities.NotifiedAsteroidsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidsDatabaseDao {

    @Transaction
    @Query("SELECT * FROM main_details WHERE id = :asteroidId")
    fun getAsteroidDetails(asteroidId: String): Flow<MainDetailsWithCloseApproachData>

    @Query("SELECT COUNT(*) FROM main_details")
    fun getItemsCount(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM main_details ORDER BY saveTime DESC")
    fun getAllAsteroidDetails(): Flow<List<MainDetailsWithCloseApproachData>>

    @Query("SELECT EXISTS(SELECT * FROM main_details WHERE id = :asteroidId)")
    fun isAsteroidDetailsExists(asteroidId: String): Flow<Boolean>

    @Transaction
    suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData) {
        insertMainDetails(mainDetails.mainDetails)
        insertCloseApproachData(mainDetails.closeApproachData)
        insertNotifiedAsteroids(NotifiedAsteroidsEntity(
            id = mainDetails.mainDetails.id
        ))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainDetails(mainDetails: MainDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCloseApproachData(closeApproachData: List<CloseApproachDataEntity>)

    @Transaction
    suspend fun deleteAsteroidDetails(asteroidId: String) {
        deleteCloseApproachData(asteroidId)
        deleteMainDetails(asteroidId)
        deleteNotifiedAsteroids(asteroidId)
    }

    @Query("DELETE FROM main_details WHERE id = :asteroidId")
    suspend fun deleteMainDetails(asteroidId: String)

    @Query("DELETE FROM close_approach_data WHERE asteroidId = :asteroidId")
    suspend fun deleteCloseApproachData(asteroidId: String)

    @Query("SELECT * FROM notified_asteroids")
    fun getNotifiedAsteroids(): Flow<List<NotifiedAsteroidsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifiedAsteroids(notifiedAsteroids: NotifiedAsteroidsEntity)

    @Query("DELETE FROM notified_asteroids WHERE id = :asteroidId")
    suspend fun deleteNotifiedAsteroids(asteroidId: String)

}