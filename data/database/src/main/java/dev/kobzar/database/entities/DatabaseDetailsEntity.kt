package dev.kobzar.database.entities

import androidx.room.Entity
import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation

//@Entity(tableName = "main_details")
//data class MainDetailsEntity(
//    @PrimaryKey val id: String,
//    val neoReferenceId: String,
//    val name: String,
//    val nasaJplUrl: String,
//    val isDangerous: Boolean,
//    val isSentryObject: Boolean,
//    @Embedded val estimatedDiameter: MainAsteroidsEstimatedDiameterDatabaseModel,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "asteroidId"
//    )
//    val closeApproachData: List<CloseApproachDataEntity>
//)

//@Entity(tableName = "close_approach_data")
//data class CloseApproachDataEntity(
//    @PrimaryKey(autoGenerate = true) val id: Long = 0,
//    val asteroidId: String,
//    val closeApproachDate: String,
//    val closeApproachDateFull: String,
//    val epochDateCloseApproach: Long,
//    @Embedded val relativeVelocity: RelativeVelocityDatabaseModel,
//    @Embedded val missDistance: MissDistanceDatabaseModel,
//    val astronomicalDistance: String,
//    val orbitingBody: String
//)

data class MainDetailsWithCloseApproachData(
    @Embedded val mainDetails: MainDetailsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "asteroidId"
    )
    val closeApproachData: List<CloseApproachDataEntity>
)

@Entity(tableName = "main_details")
data class MainDetailsEntity(
    @PrimaryKey val id: String,
    val neoReferenceId: String,
    val name: String,
    val nasaJplUrl: String,
    val isDangerous: Boolean,
    val isSentryObject: Boolean,
    @Embedded val estimatedDiameter: MainAsteroidsEstimatedDiameterDatabaseModel
)

@Entity(tableName = "close_approach_data")
data class CloseApproachDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val asteroidId: String,
    val closeApproachDate: String,
    val closeApproachDateFull: String,
    val epochDateCloseApproach: Long,
    @Embedded val relativeVelocity: RelativeVelocityDatabaseModel,
    @Embedded val missDistance: MissDistanceDatabaseModel,
    val astronomicalDistance: String,
    val orbitingBody: String
)

data class RelativeVelocityDatabaseModel(
    val kilometersPerSecond: String,
    val kilometersPerHour: String,
    val milesPerHour: String
)

data class MissDistanceDatabaseModel(
    val astronomical: String,
    val kilometers: String,
    val lunar: String,
    val miles: String
)

data class MainAsteroidsEstimatedDiameterDatabaseModel(
    @Embedded(prefix = "km_") val kilometers: MainAsteroidsDiameterDatabaseModel,
    @Embedded(prefix = "m_") val meters: MainAsteroidsDiameterDatabaseModel,
    @Embedded(prefix = "mi_") val miles: MainAsteroidsDiameterDatabaseModel,
    @Embedded(prefix = "ft_") val feet: MainAsteroidsDiameterDatabaseModel
)

data class MainAsteroidsDiameterDatabaseModel(
    val estimatedDiameterMin: Double,
    val estimatedDiameterMax: Double
)

