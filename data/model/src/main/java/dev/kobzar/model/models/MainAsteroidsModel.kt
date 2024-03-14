package dev.kobzar.model.models

import dev.kobzar.model.models.shared.MainAsteroidsEstimatedDiameter
import dev.kobzar.model.models.shared.MainAsteroidsLinks

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
