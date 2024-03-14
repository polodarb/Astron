package dev.kobzar.domain.useCases.reformatUnits

import dev.kobzar.model.models.shared.MainAsteroidsEstimatedDiameter

interface ReformatDiameterUseCase {

    suspend operator fun invoke(diameter: MainAsteroidsEstimatedDiameter): MainAsteroidsEstimatedDiameter

}