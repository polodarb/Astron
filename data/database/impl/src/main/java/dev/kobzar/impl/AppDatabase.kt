package dev.kobzar.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kobzar.impl.entities.DatabaseDetailsEntity
import dev.kobzar.impl.dao.AsteroidDetailsDao

@Database(
    entities = [
        DatabaseDetailsEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun asteroidDetailsDao(): AsteroidDetailsDao

}