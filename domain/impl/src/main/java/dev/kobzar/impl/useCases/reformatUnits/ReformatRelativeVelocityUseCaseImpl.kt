package dev.kobzar.impl.useCases.reformatUnits

import dev.kobzar.domain.useCases.reformatUnits.ReformatRelativeVelocityUseCase
import dev.kobzar.model.models.shared.RelativeVelocityModel
import dev.kobzar.platform.utils.UnitUtils
import javax.inject.Inject

class ReformatRelativeVelocityUseCaseImpl @Inject constructor(): ReformatRelativeVelocityUseCase {

    override suspend fun invoke(velocity: RelativeVelocityModel): RelativeVelocityModel {
        return RelativeVelocityModel(
            kilometersPerHour = UnitUtils.extractIntegerPart(velocity.kilometersPerHour),
            milesPerHour = UnitUtils.extractIntegerPart(velocity.milesPerHour),
            kilometersPerSecond = UnitUtils.extractIntegerPart(velocity.kilometersPerSecond),
        )
    }
}