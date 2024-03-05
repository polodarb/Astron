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

//    fun NetworkAsteroidsModel.toMainAsteroidsModel(): MainAsteroidsModel {
//        return MainAsteroidsModel(
//            elementCount = this.elementCount,
//            nearEarthObjects = this.nearEarthObjects
//                .flatMap { it.entries }
//                .associate { entry ->
//                    entry.key to entry.value.map { networkAsteroid ->
//                        MainAsteroidsListItem(
//                            id = networkAsteroid.id,
//                            neoReferenceId = networkAsteroid.neoReferenceId,
//                            name = networkAsteroid.name,
//                            estimatedDiameter = MainAsteroidsEstimatedDiameter(
//                                kilometers = networkAsteroid.estimatedDiameter.kilometers,
//                                meters = networkAsteroid.estimatedDiameter.meters,
//                                feet = networkAsteroid.estimatedDiameter.feet
//                            ),
//                            isDangerous = networkAsteroid.isPotentiallyHazardousAsteroid,
//                            closeApproachData = networkAsteroid.closeApproachData.map { closeApproachData ->
//                                MainAsteroidsCloseApproachData(
//                                    closeApproachDate = closeApproachData.closeApproachDate,
//                                    orbitingBody = closeApproachData.orbitingBody
//                                )
//                            },
//                            isSentryObject = networkAsteroid.isSentryObject
//                        )
//                    }
//                },
//            pageKeys = MainAsteroidsLinks(
//                next = this.pageKeys.next,
//                prev = this.pageKeys.prev,
//                self = this.pageKeys.self
//            )
//        )
//    }

    fun NetworkLinks.toMainAsteroidsLinks(): MainAsteroidsLinks {
        return MainAsteroidsLinks(
            next = this.next,
            prev = this.prev,
            self = this.self
        )
    }

    fun NetworkAsteroid.toMainAsteroidsListItem(): MainAsteroidsListItem {
        return MainAsteroidsListItem(
            id = this.id,
            neoReferenceId = this.neoReferenceId,
            name = this.name,
            estimatedDiameter = MainAsteroidsEstimatedDiameter(
                kilometers = MainAsteroidsDiameter(this.estimatedDiameter.kilometers.estimatedDiameterMin, this.estimatedDiameter.kilometers.estimatedDiameterMax),
                meters = MainAsteroidsDiameter(this.estimatedDiameter.meters.estimatedDiameterMin, this.estimatedDiameter.meters.estimatedDiameterMax),
                miles = MainAsteroidsDiameter(this.estimatedDiameter.miles.estimatedDiameterMin, this.estimatedDiameter.miles.estimatedDiameterMax),
                feet = MainAsteroidsDiameter(this.estimatedDiameter.feet.estimatedDiameterMin, this.estimatedDiameter.feet.estimatedDiameterMax)
            ),
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