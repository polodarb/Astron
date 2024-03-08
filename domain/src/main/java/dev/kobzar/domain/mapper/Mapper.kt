package dev.kobzar.domain.mapper

import android.util.Log
import dev.kobzar.platform.utils.UnitUtils
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.DiameterUnit.FEET
import dev.kobzar.preferences.model.DiameterUnit.KILOMETER
import dev.kobzar.preferences.model.DiameterUnit.METER
import dev.kobzar.preferences.model.DiameterUnit.MILE
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.repository.models.MainDetailsCloseApproachData
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.models.PrefsDetailsCloseApproachData
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.repository.models.shared.MainAsteroidsDiameter

fun MainDetailsModel.toCorrectedFormatModel(): MainDetailsModel {
    return MainDetailsModel(
        id = this.id,
        neoReferenceId = this.neoReferenceId,
        name = this.name,
        nasaJplUrl = this.nasaJplUrl,
        isDangerous = isDangerous,
        estimatedDiameter = this.estimatedDiameter.copy(
            kilometers = copyAndRound(this.estimatedDiameter.kilometers),
            meters = copyAndRound(this.estimatedDiameter.meters),
            miles = copyAndRound(this.estimatedDiameter.miles),
            feet = copyAndRound(this.estimatedDiameter.feet)
        ),
        closeApproachData = this.closeApproachData,
        isSentryObject = this.isSentryObject
    )
}

private fun copyAndRound(diameter: MainAsteroidsDiameter): MainAsteroidsDiameter {
    return MainAsteroidsDiameter(
        estimatedDiameterMax = UnitUtils.roundDouble(diameter.estimatedDiameterMax),
        estimatedDiameterMin = UnitUtils.roundDouble(diameter.estimatedDiameterMin)
    )
}

fun MainDetailsModel.toPrefsDetailsModel(
    diameterUnit: DiameterUnit?,
    missDistanceUnit: MissDistanceUnit?,
    relativeVelocityUnit: RelativeVelocityUnit?,
    closestApproach: MainDetailsCloseApproachData
): PrefsDetailsModel {
    Log.e("Mapper", "toPrefsDetailsModel - $diameterUnit")
    val estimatedDiameter = when (diameterUnit) {
        KILOMETER -> this.estimatedDiameter.kilometers
        METER -> this.estimatedDiameter.meters
        MILE -> this.estimatedDiameter.miles
        FEET -> this.estimatedDiameter.feet
        else -> this.estimatedDiameter.kilometers
    }

    val diameterMax = UnitUtils.roundDouble(estimatedDiameter.estimatedDiameterMax, 3)
    val diameterMin = UnitUtils.roundDouble(estimatedDiameter.estimatedDiameterMin, 3)

    val relativeVelocityKmPerSecond = UnitUtils.extractIntegerPart(closestApproach.relativeVelocity.kilometersPerSecond)
    val relativeVelocityKmPerHour = UnitUtils.extractIntegerPart(closestApproach.relativeVelocity.kilometersPerHour)
    val relativeVelocityMilesPerHour = UnitUtils.extractIntegerPart(closestApproach.relativeVelocity.milesPerHour)

    val missDistanceAstronomical = UnitUtils.roundDouble(closestApproach.missDistance.astronomical.toDouble())
    val missDistanceLunar = UnitUtils.roundDouble(closestApproach.missDistance.lunar.toDouble())
    val missDistanceKm = UnitUtils.roundDouble(closestApproach.missDistance.kilometers.toDouble())
    val missDistanceMiles = UnitUtils.roundDouble(closestApproach.missDistance.miles.toDouble())

    val missDistance = when (missDistanceUnit) {
        MissDistanceUnit.KILOMETER -> "$missDistanceKm Km"
        MissDistanceUnit.MILE -> "$missDistanceMiles Miles"
        MissDistanceUnit.ASTRONOMICAL -> "$missDistanceAstronomical AU"
        MissDistanceUnit.LUNAR -> "$missDistanceLunar LUN"
        else -> closestApproach.missDistance.kilometers
    }

    val relativeVelocity = when (relativeVelocityUnit) {
        RelativeVelocityUnit.KM_S -> "$relativeVelocityKmPerSecond km/s"
        RelativeVelocityUnit.KM_H -> "$relativeVelocityKmPerHour km/h"
        RelativeVelocityUnit.MILE_H -> "$relativeVelocityMilesPerHour mph"
        else -> closestApproach.relativeVelocity.kilometersPerSecond
    }

    return PrefsDetailsModel(
        id = this.id,
        name = this.name,
        neoReferenceId = this.neoReferenceId,
        nasaJplUrl = this.nasaJplUrl,
        isDangerous = this.isDangerous,
        estimatedDiameter = MainAsteroidsDiameter(
            estimatedDiameterMax = diameterMax,
            estimatedDiameterMin = diameterMin
        ),
        closeApproachData = PrefsDetailsCloseApproachData(
            closeApproachDate = closestApproach.closeApproachDate ,
            closeApproachDateFull = closestApproach.closeApproachDateFull,
            epochDateCloseApproach = closestApproach.epochDateCloseApproach,
            relativeVelocity = relativeVelocity,
            missDistance = missDistance,
            astronomicalDistance = closestApproach.astronomicalDistance,
            orbitingBody = closestApproach.orbitingBody
        ),
        isSentryObject = this.isSentryObject
    )
}

//fun UiState<MainDetailsModel>.toUiStatePrefsDetailsModel(
//    diameterUnit: DiameterUnit?,
//    closestApproach: MainDetailsCloseApproachData
//): UiState<PrefsDetailsModel> {
//    return when (this) {
//        is UiState.Success -> UiState.Success(
//            this.data.toPrefsDetailsModel(
//                diameterUnit,
//                closestApproach,
//            )
//        )
//
//        is UiState.Error -> UiState.Error(this.throwable)
//        is UiState.Loading -> UiState.Loading()
//    }
//}