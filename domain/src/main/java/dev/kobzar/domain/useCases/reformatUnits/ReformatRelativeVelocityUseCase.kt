package dev.kobzar.domain.useCases.reformatUnits

import dev.kobzar.model.models.shared.RelativeVelocityModel

interface ReformatRelativeVelocityUseCase {

    suspend operator fun invoke(velocity: RelativeVelocityModel): RelativeVelocityModel

}