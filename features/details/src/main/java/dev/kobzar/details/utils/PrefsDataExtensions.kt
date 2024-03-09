package dev.kobzar.details.utils

import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.models.MainDetailsCloseApproachData
import dev.kobzar.repository.models.shared.MainAsteroidsEstimatedDiameter

object PrefsDataExtensions {

    fun MainDetailsCloseApproachData.getMissDistanceUnit(userPrefs: UserPreferencesModel?): String? {
        return when (userPrefs?.missDistanceUnits) {
            MissDistanceUnit.LUNAR -> missDistance.lunar
            MissDistanceUnit.KILOMETER -> missDistance.kilometers
            MissDistanceUnit.MILE -> missDistance.miles
            MissDistanceUnit.ASTRONOMICAL -> missDistance.astronomical
            else -> null
        }
    }

    fun MainDetailsCloseApproachData.getRelativeVelocity(userPrefs: UserPreferencesModel?): String? {
        return when (userPrefs?.relativeVelocityUnits) {
            RelativeVelocityUnit.MILE_H -> relativeVelocity.milesPerHour
            RelativeVelocityUnit.KM_S -> relativeVelocity.kilometersPerSecond
            RelativeVelocityUnit.KM_H -> relativeVelocity.kilometersPerHour
            else -> null
        }
    }

    fun MainAsteroidsEstimatedDiameter.getDiameterRangeByUnit(diameterUnit: DiameterUnit): Pair<Double?, Double?> {
        return when (diameterUnit) {
            DiameterUnit.KILOMETER -> kilometers.estimatedDiameterMin to kilometers.estimatedDiameterMax
            DiameterUnit.METER -> meters.estimatedDiameterMin to meters.estimatedDiameterMax
            DiameterUnit.MILE -> miles.estimatedDiameterMin to miles.estimatedDiameterMax
            DiameterUnit.FEET -> feet.estimatedDiameterMin to feet.estimatedDiameterMax
        }
    }


}