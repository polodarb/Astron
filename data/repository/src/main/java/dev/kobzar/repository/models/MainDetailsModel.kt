package dev.kobzar.repository.models

import dev.kobzar.network.models.NetworkMissDistance
import dev.kobzar.network.models.NetworkRelativeVelocity
import dev.kobzar.repository.models.shared.MainAsteroidsDiameter
import dev.kobzar.repository.models.shared.MainAsteroidsEstimatedDiameter

data class MainDetailsModel(
    val id: String,
    val neoReferenceId: String,
    val name: String,
    val nasaJplUrl: String,
    val isDangerous: Boolean,
    val estimatedDiameter: MainAsteroidsEstimatedDiameter,
    val closeApproachData: List<MainDetailsCloseApproachData>,
    val isSentryObject: Boolean
)

data class PrefsDetailsModel(
    val id: String,
    val neoReferenceId: String,
    val name: String,
    val nasaJplUrl: String,
    val isDangerous: Boolean,
    val estimatedDiameter: MainAsteroidsDiameter,
    val closeApproachData: PrefsDetailsCloseApproachData,
    val isSentryObject: Boolean
)

data class PrefsDetailsCloseApproachData(
    val closeApproachDate: String,
    val closeApproachDateFull: String,
    val epochDateCloseApproach: Long,
    val relativeVelocity: String,
    val missDistance: String,
    val astronomicalDistance: String,
    val orbitingBody: String
)

data class MainDetailsCloseApproachData(
    val closeApproachDate: String,
    val closeApproachDateFull: String,
    val epochDateCloseApproach: Long,
    val relativeVelocity: NetworkRelativeVelocity,
    val missDistance: NetworkMissDistance,
    val astronomicalDistance: String,
    val orbitingBody: String
)