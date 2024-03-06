package dev.kobzar.repository.mappers

import dev.kobzar.network.models.NetworkAsteroid
import dev.kobzar.network.models.NetworkAsteroidsModel
import dev.kobzar.network.models.NetworkCloseApproachData
import dev.kobzar.network.models.NetworkLinks
import dev.kobzar.repository.models.MainAsteroidsCloseApproachData
import dev.kobzar.repository.models.MainAsteroidsDiameter
import dev.kobzar.repository.models.MainAsteroidsEstimatedDiameter
import dev.kobzar.repository.models.MainAsteroidsLinks
import dev.kobzar.repository.models.MainAsteroidsListItem
import dev.kobzar.repository.models.MainAsteroidsModel

object ModelsMapper {

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