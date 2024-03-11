package dev.kobzar.repository.mappers

import dev.kobzar.database.entities.CloseApproachDataEntity
import dev.kobzar.database.entities.MainAsteroidsDiameterDatabaseModel
import dev.kobzar.database.entities.MainAsteroidsEstimatedDiameterDatabaseModel
import dev.kobzar.database.entities.MainDetailsEntity
import dev.kobzar.database.entities.MainDetailsWithCloseApproachData
import dev.kobzar.database.entities.MissDistanceDatabaseModel
import dev.kobzar.database.entities.RelativeVelocityDatabaseModel
import dev.kobzar.repository.models.MainDetailsCloseApproachData
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.models.shared.MainAsteroidsDiameter
import dev.kobzar.repository.models.shared.MainAsteroidsEstimatedDiameter
import dev.kobzar.repository.models.shared.MissDistanceModel
import dev.kobzar.repository.models.shared.RelativeVelocityModel

object DatabaseMapper {

    fun List<MainDetailsWithCloseApproachData>.toListMainDetailsModel(): List<MainDetailsModel> {
        return this.map { data ->
            MainDetailsModel(
                id = data.mainDetails.id,
                neoReferenceId = data.mainDetails.neoReferenceId,
                name = data.mainDetails.name,
                isSentryObject = data.mainDetails.isSentryObject,
                nasaJplUrl = data.mainDetails.nasaJplUrl,
                isDangerous = data.mainDetails.isDangerous,
                estimatedDiameter = MainAsteroidsEstimatedDiameter(
                    kilometers = MainAsteroidsDiameter(
                        estimatedDiameterMax = data.mainDetails.estimatedDiameter.kilometers.estimatedDiameterMax,
                        estimatedDiameterMin = data.mainDetails.estimatedDiameter.kilometers.estimatedDiameterMin
                    ),
                    meters = MainAsteroidsDiameter(
                        estimatedDiameterMax = data.mainDetails.estimatedDiameter.meters.estimatedDiameterMax,
                        estimatedDiameterMin = data.mainDetails.estimatedDiameter.meters.estimatedDiameterMin
                    ),
                    miles = MainAsteroidsDiameter(
                        estimatedDiameterMax = data.mainDetails.estimatedDiameter.miles.estimatedDiameterMax,
                        estimatedDiameterMin = data.mainDetails.estimatedDiameter.miles.estimatedDiameterMin
                    ),
                    feet = MainAsteroidsDiameter(
                        estimatedDiameterMax = data.mainDetails.estimatedDiameter.feet.estimatedDiameterMax,
                        estimatedDiameterMin = data.mainDetails.estimatedDiameter.feet.estimatedDiameterMin
                    )
                ),
                closeApproachData = data.closeApproachData.map {
                    MainDetailsCloseApproachData(
                        closeApproachDate = it.closeApproachDate,
                        orbitingBody = it.orbitingBody,
                        closeApproachDateFull = it.closeApproachDateFull,
                        epochDateCloseApproach = it.epochDateCloseApproach,
                        astronomicalDistance = it.astronomicalDistance,
                        relativeVelocity = RelativeVelocityModel(
                            kilometersPerSecond = it.relativeVelocity.kilometersPerSecond,
                            kilometersPerHour = it.relativeVelocity.kilometersPerHour,
                            milesPerHour = it.relativeVelocity.milesPerHour
                        ),
                        missDistance = MissDistanceModel(
                            astronomical = it.missDistance.astronomical,
                            lunar = it.missDistance.lunar,
                            kilometers = it.missDistance.kilometers,
                            miles = it.missDistance.miles
                        )
                    )
                }
            )
        }
    }

    fun MainDetailsWithCloseApproachData.toMainDetailsModel(): MainDetailsModel {
        return MainDetailsModel(
            id = this.mainDetails.id,
            neoReferenceId = this.mainDetails.neoReferenceId,
            name = this.mainDetails.name,
            isSentryObject = this.mainDetails.isSentryObject,
            nasaJplUrl = this.mainDetails.nasaJplUrl,
            isDangerous = this.mainDetails.isDangerous,
            estimatedDiameter = MainAsteroidsEstimatedDiameter(
                kilometers = MainAsteroidsDiameter(
                    estimatedDiameterMax = this.mainDetails.estimatedDiameter.kilometers.estimatedDiameterMax,
                    estimatedDiameterMin = this.mainDetails.estimatedDiameter.kilometers.estimatedDiameterMin
                ),
                meters = MainAsteroidsDiameter(
                    estimatedDiameterMax = this.mainDetails.estimatedDiameter.meters.estimatedDiameterMax,
                    estimatedDiameterMin = this.mainDetails.estimatedDiameter.meters.estimatedDiameterMin
                ),
                miles = MainAsteroidsDiameter(
                    estimatedDiameterMax = this.mainDetails.estimatedDiameter.miles.estimatedDiameterMax,
                    estimatedDiameterMin = this.mainDetails.estimatedDiameter.miles.estimatedDiameterMin),
                feet = MainAsteroidsDiameter(
                    estimatedDiameterMax = this.mainDetails.estimatedDiameter.feet.estimatedDiameterMax,
                    estimatedDiameterMin = this.mainDetails.estimatedDiameter.feet.estimatedDiameterMin
                )),
            closeApproachData = this.closeApproachData.map {
                MainDetailsCloseApproachData(
                    closeApproachDate = it.closeApproachDate,
                    orbitingBody = it.orbitingBody,
                    closeApproachDateFull = it.closeApproachDateFull,
                    epochDateCloseApproach = it.epochDateCloseApproach,
                    astronomicalDistance = it.astronomicalDistance,
                    relativeVelocity = RelativeVelocityModel(
                        kilometersPerSecond = it.relativeVelocity.kilometersPerSecond,
                        kilometersPerHour = it.relativeVelocity.kilometersPerHour,
                        milesPerHour = it.relativeVelocity.milesPerHour
                    ),
                    missDistance = MissDistanceModel(
                        astronomical = it.missDistance.astronomical,
                        lunar = it.missDistance.lunar,
                        kilometers = it.missDistance.kilometers,
                        miles = it.missDistance.miles
                    )
                )
            }
        )
    }

    fun MainDetailsModel.toMainDetailsWithCloseApproachData(saveTime: Long): MainDetailsWithCloseApproachData {
        return MainDetailsWithCloseApproachData(
            mainDetails = this.toMainDetailsEntity(saveTime = saveTime),
            closeApproachData = this.closeApproachData.map {
                CloseApproachDataEntity(
                    asteroidId = this.id,
                    closeApproachDate = it.closeApproachDate,
                    relativeVelocity = RelativeVelocityDatabaseModel(
                        kilometersPerSecond = it.relativeVelocity.kilometersPerSecond,
                        kilometersPerHour = it.relativeVelocity.kilometersPerHour,
                        milesPerHour = it.relativeVelocity.milesPerHour
                    ),
                    missDistance = MissDistanceDatabaseModel(
                        astronomical = it.missDistance.astronomical,
                        lunar = it.missDistance.lunar,
                        kilometers = it.missDistance.kilometers,
                        miles = it.missDistance.miles
                    ),
                    orbitingBody = it.orbitingBody,
                    closeApproachDateFull = it.closeApproachDateFull,
                    epochDateCloseApproach = it.epochDateCloseApproach,
                    astronomicalDistance = it.astronomicalDistance
                )
            },
        )
    }

    private fun MainDetailsModel.toMainDetailsEntity(saveTime: Long): MainDetailsEntity {
        return MainDetailsEntity(
            id = this.id,
            name = this.name,
            neoReferenceId = neoReferenceId,
            estimatedDiameter = MainAsteroidsEstimatedDiameterDatabaseModel(
                kilometers = MainAsteroidsDiameterDatabaseModel(
                    estimatedDiameterMax = this.estimatedDiameter.kilometers.estimatedDiameterMax,
                    estimatedDiameterMin = this.estimatedDiameter.kilometers.estimatedDiameterMin
                ),
                meters = MainAsteroidsDiameterDatabaseModel(
                    estimatedDiameterMax = this.estimatedDiameter.meters.estimatedDiameterMax,
                    estimatedDiameterMin = this.estimatedDiameter.meters.estimatedDiameterMin
                ),
                miles = MainAsteroidsDiameterDatabaseModel(
                    estimatedDiameterMax = this.estimatedDiameter.miles.estimatedDiameterMax,
                    estimatedDiameterMin = this.estimatedDiameter.miles.estimatedDiameterMin
                ),
                feet = MainAsteroidsDiameterDatabaseModel(
                    estimatedDiameterMax = this.estimatedDiameter.feet.estimatedDiameterMax,
                    estimatedDiameterMin = this.estimatedDiameter.feet.estimatedDiameterMin
                )
            ),
            nasaJplUrl = this.nasaJplUrl,
            isDangerous = this.isDangerous,
            isSentryObject = this.isSentryObject,
            saveTime = saveTime
        )
    }

}