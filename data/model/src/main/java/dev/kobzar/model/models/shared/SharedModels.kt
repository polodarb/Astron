package dev.kobzar.model.models.shared

data class MainAsteroidsEstimatedDiameter(
    val kilometers: MainAsteroidsDiameter,
    val meters: MainAsteroidsDiameter,
    val miles: MainAsteroidsDiameter,
    val feet: MainAsteroidsDiameter
)

data class MainAsteroidsDiameter(
    val estimatedDiameterMin: Double,
    val estimatedDiameterMax: Double
)

data class MainAsteroidsLinks(
    val next: String,
    val prev: String,
    val self: String
)

data class RelativeVelocityModel(
    val kilometersPerSecond: String,
    val kilometersPerHour: String,
    val milesPerHour: String
)

data class MissDistanceModel(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)