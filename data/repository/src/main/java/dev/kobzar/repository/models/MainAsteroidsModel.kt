package dev.kobzar.repository.models

data class MainAsteroidsModel (
    val elementCount: Int,
    val nearEarthObjects: Map<String, List<MainAsteroidsListItem>>,
    val pageKeys: MainAsteroidsLinks
)

data class MainAsteroidsListItem(
    val id: String,
    val neoReferenceId: String,
    val name: String,
    val estimatedDiameter: MainAsteroidsEstimatedDiameter,
    val isDangerous: Boolean,
    val closeApproachData: List<MainAsteroidsCloseApproachData>,
    val isSentryObject: Boolean
)

data class MainAsteroidsCloseApproachData(
    val closeApproachDate: String,
    val orbitingBody: String
)

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
