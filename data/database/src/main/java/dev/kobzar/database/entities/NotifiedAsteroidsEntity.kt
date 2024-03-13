package dev.kobzar.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notified_asteroids")
data class NotifiedAsteroidsEntity(
    @PrimaryKey val id: String
)