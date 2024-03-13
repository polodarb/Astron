package dev.kobzar.model.models

import dev.kobzar.model.models.shared.MainAsteroidsEstimatedDiameter
import dev.kobzar.model.models.shared.MissDistanceModel
import dev.kobzar.model.models.shared.RelativeVelocityModel

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

data class MainDetailsCloseApproachData(
    val closeApproachDate: String,
    val closeApproachDateFull: String,
    val epochDateCloseApproach: Long,
    val relativeVelocity: RelativeVelocityModel,
    val missDistance: MissDistanceModel,
    val astronomicalDistance: String,
    val orbitingBody: String
)