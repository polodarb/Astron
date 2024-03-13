package dev.kobzar.model.mappers

import dev.kobzar.network.models.NetworkAsteroid
import dev.kobzar.network.models.NetworkAsteroidsModel
import dev.kobzar.network.models.NetworkCloseApproachData
import dev.kobzar.network.models.NetworkLinks
import dev.kobzar.model.models.MainAsteroidsCloseApproachData
import dev.kobzar.model.models.MainAsteroidsListItem
import dev.kobzar.model.models.MainAsteroidsModel
import dev.kobzar.model.models.MainDetailsCloseApproachData
import dev.kobzar.model.models.MainDetailsModel
import dev.kobzar.model.models.shared.MainAsteroidsDiameter
import dev.kobzar.model.models.shared.MainAsteroidsEstimatedDiameter
import dev.kobzar.model.models.shared.MainAsteroidsLinks
import dev.kobzar.model.models.shared.MissDistanceModel
import dev.kobzar.model.models.shared.RelativeVelocityModel

object NetworkMapper {

    fun NetworkAsteroid.toMainDetailsModel(): MainDetailsModel {
        return MainDetailsModel(
            id = this.id,
            name = this.name,
            neoReferenceId = this.neoReferenceId,
            nasaJplUrl = this.nasaJplUrl,
            isDangerous = this.isPotentiallyHazardousAsteroid,
            estimatedDiameter = with(this.estimatedDiameter) {
                MainAsteroidsEstimatedDiameter(
                    kilometers = MainAsteroidsDiameter(kilometers.estimatedDiameterMin, kilometers.estimatedDiameterMax),
                    meters = MainAsteroidsDiameter(meters.estimatedDiameterMin, meters.estimatedDiameterMax),
                    miles = MainAsteroidsDiameter(miles.estimatedDiameterMin, miles.estimatedDiameterMax),
                    feet = MainAsteroidsDiameter(feet.estimatedDiameterMin, feet.estimatedDiameterMax)
                )
            },
            closeApproachData = this.closeApproachData.map { it.toMainDetailsCloseApproachData() },
            isSentryObject = this.isSentryObject
        )
    }

    private fun NetworkLinks.toMainAsteroidsLinks(): MainAsteroidsLinks {
        return MainAsteroidsLinks(
            next = this.next,
            prev = this.prev,
            self = this.self
        )
    }

    private fun NetworkAsteroid.toMainAsteroidsListItem(): MainAsteroidsListItem {
        return MainAsteroidsListItem(
            id = this.id,
            neoReferenceId = this.neoReferenceId,
            name = this.name,
            estimatedDiameter = with(this.estimatedDiameter) {
                MainAsteroidsEstimatedDiameter(
                    kilometers = MainAsteroidsDiameter(kilometers.estimatedDiameterMin, kilometers.estimatedDiameterMax),
                    meters = MainAsteroidsDiameter(meters.estimatedDiameterMin, meters.estimatedDiameterMax),
                    miles = MainAsteroidsDiameter(miles.estimatedDiameterMin, miles.estimatedDiameterMax),
                    feet = MainAsteroidsDiameter(feet.estimatedDiameterMin, feet.estimatedDiameterMax)
                )
            },
            isDangerous = this.isPotentiallyHazardousAsteroid,
            closeApproachData = this.closeApproachData.map { it.toMainAsteroidsCloseApproachData() },
            isSentryObject = this.isSentryObject
        )
    }

    private fun NetworkCloseApproachData.toMainAsteroidsCloseApproachData(): MainAsteroidsCloseApproachData {
        return MainAsteroidsCloseApproachData(
            closeApproachDate = this.closeApproachDate,
            orbitingBody = this.orbitingBody
        )
    }

    private fun NetworkCloseApproachData.toMainDetailsCloseApproachData(): MainDetailsCloseApproachData {
        return MainDetailsCloseApproachData(
            closeApproachDate = this.closeApproachDate,
            closeApproachDateFull = this.closeApproachDateFull,
            epochDateCloseApproach = this.epochDateCloseApproach,
            orbitingBody = this.orbitingBody,
            relativeVelocity = RelativeVelocityModel(
                kilometersPerSecond = this.relativeVelocity.kilometersPerSecond,
                kilometersPerHour = this.relativeVelocity.kilometersPerHour,
                milesPerHour = this.relativeVelocity.milesPerHour
            ),
            missDistance = MissDistanceModel(
                astronomical = this.missDistance.astronomical,
                lunar = this.missDistance.lunar,
                kilometers = this.missDistance.kilometers,
                miles = this.missDistance.miles
            ),
            astronomicalDistance = this.missDistance.astronomical
        )
    }

    fun NetworkAsteroidsModel.toMainAsteroidsModel(): MainAsteroidsModel {
        return MainAsteroidsModel(
            elementCount = this.elementCount,
            nearEarthObjects = this.nearEarthObjects.mapValues { (_, networkAsteroids) ->
                networkAsteroids.map { it.toMainAsteroidsListItem() }
            },
            pageKeys = this.pageKeys.toMainAsteroidsLinks()
        )
    }

}