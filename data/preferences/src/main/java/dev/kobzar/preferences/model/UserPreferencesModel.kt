package dev.kobzar.preferences.model

data class UserPreferencesModel(
    val diameterUnits: DiameterUnit,
    val relativeVelocityUnits: RelativeVelocityUnit,
    val missDistanceUnits: MissDistanceUnit
)

enum class DiameterUnit {
    KILOMETER,
    METER,
    MILE,
    FEET
}

enum class RelativeVelocityUnit {
    KM_S,
    KM_H,
    MILE_H
}

enum class MissDistanceUnit {
    KILOMETER,
    MILE,
    LUNAR,
    ASTRONOMICAL
}