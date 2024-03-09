package dev.kobzar.domain.useCases.reformatUnits

import dev.kobzar.repository.models.shared.MissDistanceModel

interface ReformatMissDistanceUseCase {

    suspend operator fun invoke(missDistance: MissDistanceModel): MissDistanceModel

}