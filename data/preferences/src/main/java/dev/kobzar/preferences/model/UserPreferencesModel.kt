package dev.kobzar.preferences.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesModel(
    val diameterUnits: DiameterUnit,
    val relativeVelocityUnits: RelativeVelocityUnit,
    val missDistanceUnits: MissDistanceUnit
)

@Serializable
enum class DiameterUnit {
    KILOMETER,
    METER,
    MILE,
    FEET
}

@Serializable
enum class RelativeVelocityUnit {
    KM_S,
    KM_H,
    MILE_H
}

@Serializable
enum class MissDistanceUnit {
    KILOMETER,
    MILE,
    LUNAR,
    ASTRONOMICAL
}
