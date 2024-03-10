package dev.kobzar.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kobzar.database.entities.CloseApproachDataEntity
import dev.kobzar.database.entities.MainDetailsEntity
import dev.kobzar.impl.dao.AsteroidDetailsDao

@Database(
    entities = [
        MainDetailsEntity::class,
        CloseApproachDataEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun asteroidDetailsDao(): AsteroidDetailsDao

}