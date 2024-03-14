package dev.kobzar.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAsteroidsModel(
    @SerialName("element_count")
    val elementCount: Int,
    @SerialName("near_earth_objects")
    val nearEarthObjects: Map<String, List<NetworkAsteroid>>,
    @SerialName("links")
    val pageKeys: NetworkLinks
)

@Serializable
data class NetworkAsteroid(
    val links: NetworkAsteroidLinks,
    val id: String,
    @SerialName("neo_reference_id")
    val neoReferenceId: String,
    val name: String,
    @SerialName("nasa_jpl_url")
    val nasaJplUrl: String,
    @SerialName("absolute_magnitude_h")
    val absoluteMagnitudeH: Double,
    @SerialName("estimated_diameter")
    val estimatedDiameter: NetworkEstimatedDiameter,
    @SerialName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,
    @SerialName("close_approach_data")
    val closeApproachData: List<NetworkCloseApproachData>,
    @SerialName("is_sentry_object")
    val isSentryObject: Boolean
)

@Serializable
data class NetworkAsteroidLinks(
    val self: String
)

@Serializable
data class NetworkEstimatedDiameter(
    val kilometers: NetworkDiameter,
    val meters: NetworkDiameter,
    val miles: NetworkDiameter,
    val feet: NetworkDiameter
)

@Serializable
data class NetworkDiameter(
    @SerialName("estimated_diameter_min")
    val estimatedDiameterMin: Double,
    @SerialName("estimated_diameter_max")
    val estimatedDiameterMax: Double
)

@Serializable
data class NetworkCloseApproachData(
    @SerialName("close_approach_date")
    val closeApproachDate: String,
    @SerialName("close_approach_date_full")
    val closeApproachDateFull: String,
    @SerialName("epoch_date_close_approach")
    val epochDateCloseApproach: Long,
    @SerialName("relative_velocity")
    val relativeVelocity: NetworkRelativeVelocity,
    @SerialName("miss_distance")
    val missDistance: NetworkMissDistance,
    @SerialName("orbiting_body")
    val orbitingBody: String
)

@Serializable
data class NetworkRelativeVelocity(
    @SerialName("kilometers_per_second")
    val kilometersPerSecond: String,
    @SerialName("kilometers_per_hour")
    val kilometersPerHour: String,
    @SerialName("miles_per_hour")
    val milesPerHour: String
)

@Serializable
data class NetworkMissDistance(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

@Serializable
data class NetworkLinks(
    val next: String,
    val prev: String,
    val self: String
)
