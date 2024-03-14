package dev.kobzar.impl.useCases.reformatUnits

import dev.kobzar.domain.useCases.reformatUnits.ReformatDiameterUseCase
import dev.kobzar.model.models.shared.MainAsteroidsDiameter
import dev.kobzar.model.models.shared.MainAsteroidsEstimatedDiameter
import dev.kobzar.platform.utils.UnitUtils
import javax.inject.Inject

class ReformatDiameterUseCaseImpl @Inject constructor(): ReformatDiameterUseCase {

    override suspend fun invoke(diameter: MainAsteroidsEstimatedDiameter): MainAsteroidsEstimatedDiameter {
        return MainAsteroidsEstimatedDiameter(
            kilometers = MainAsteroidsDiameter(
                estimatedDiameterMax = UnitUtils.roundDouble(diameter.kilometers.estimatedDiameterMax),
                estimatedDiameterMin = UnitUtils.roundDouble(diameter.kilometers.estimatedDiameterMin)
            ),
            meters = MainAsteroidsDiameter(
                estimatedDiameterMax = UnitUtils.roundDouble(diameter.meters.estimatedDiameterMax),
                estimatedDiameterMin = UnitUtils.roundDouble(diameter.meters.estimatedDiameterMin)
            ),
            feet = MainAsteroidsDiameter(
                estimatedDiameterMax = UnitUtils.roundDouble(diameter.feet.estimatedDiameterMax),
                estimatedDiameterMin = UnitUtils.roundDouble(diameter.feet.estimatedDiameterMin)
            ),
            miles = MainAsteroidsDiameter(
                estimatedDiameterMax = UnitUtils.roundDouble(diameter.miles.estimatedDiameterMax),
                estimatedDiameterMin = UnitUtils.roundDouble(diameter.miles.estimatedDiameterMin)
            )
        )
    }

}