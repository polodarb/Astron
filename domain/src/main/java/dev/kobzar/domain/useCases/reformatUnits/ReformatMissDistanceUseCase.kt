package dev.kobzar.domain.useCases.reformatUnits

import dev.kobzar.model.models.shared.MissDistanceModel

interface ReformatMissDistanceUseCase {

    suspend operator fun invoke(missDistance: MissDistanceModel): MissDistanceModel

}