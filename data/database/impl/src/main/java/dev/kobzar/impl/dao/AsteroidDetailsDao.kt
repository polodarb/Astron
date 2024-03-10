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
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDetailsDao {

    @Transaction
    suspend fun insertAsteroidDetails(mainDetails: MainDetailsWithCloseApproachData) {
        insertMainDetails(mainDetails.mainDetails)
        insertCloseApproachData(mainDetails.closeApproachData[0]) // test
    }

    @Insert
    suspend fun insertMainDetails(mainDetails: MainDetailsEntity)

    @Insert
    suspend fun insertCloseApproachData(closeApproachData: CloseApproachDataEntity)

//    @Insert
//    suspend fun insertAsteroidDetails(mainDetails: MainDetailsEntity)

    @Transaction
    @Query("SELECT * FROM main_details WHERE id = :asteroidId")
    fun getAsteroidDetails(asteroidId: String): Flow<MainDetailsWithCloseApproachData>

    @Transaction
    @Query("SELECT * FROM main_details")
    fun getAllAsteroidDetails(): Flow<List<MainDetailsWithCloseApproachData>>

    @Query("DELETE FROM main_details WHERE id = :asteroidId")
    suspend fun deleteAsteroidDetails(asteroidId: String)

}