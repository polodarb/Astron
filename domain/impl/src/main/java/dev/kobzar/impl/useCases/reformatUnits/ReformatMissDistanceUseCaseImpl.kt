package dev.kobzar.impl.useCases.reformatUnits

import dev.kobzar.domain.useCases.reformatUnits.ReformatMissDistanceUseCase
import dev.kobzar.platform.utils.UnitUtils
import dev.kobzar.repository.models.shared.MissDistanceModel
import javax.inject.Inject

class ReformatMissDistanceUseCaseImpl @Inject constructor(): ReformatMissDistanceUseCase {

    override suspend fun invoke(missDistance: MissDistanceModel): MissDistanceModel {
        return MissDistanceModel(
            astronomical = UnitUtils.roundDouble(missDistance.astronomical.toDouble())
                .toString(),
            lunar = UnitUtils.roundDouble(missDistance.lunar.toDouble())
                .toString(),
            kilometers = UnitUtils.extractIntegerPart(missDistance.kilometers),
            miles = UnitUtils.extractIntegerPart(missDistance.miles)
        )
    }
}