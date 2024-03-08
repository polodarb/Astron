package dev.kobzar.repository.models.shared

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
