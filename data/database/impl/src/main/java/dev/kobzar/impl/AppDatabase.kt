package dev.kobzar.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kobzar.database.entities.CloseApproachDataEntity
import dev.kobzar.database.entities.MainDetailsEntity
import dev.kobzar.database.entities.NotifiedAsteroidsEntity
import dev.kobzar.impl.dao.AsteroidsDatabaseDao

@Database(
    entities = [
        MainDetailsEntity::class,
        CloseApproachDataEntity::class,
        NotifiedAsteroidsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun asteroidsDatabaseDao(): AsteroidsDatabaseDao

}