package dev.kobzar.domain.useCases.reformatUnits

import dev.kobzar.repository.models.shared.MainAsteroidsEstimatedDiameter

interface ReformatDiameterUseCase {

    suspend operator fun invoke(diameter: MainAsteroidsEstimatedDiameter): MainAsteroidsEstimatedDiameter

}