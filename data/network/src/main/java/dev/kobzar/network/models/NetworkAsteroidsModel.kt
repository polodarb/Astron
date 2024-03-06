package dev.kobzar.network.models

import com.google.gson.annotations.SerializedName

data class NetworkAsteroidsModel(
    @SerializedName("element_count")
    val elementCount: Int,
    @SerializedName("near_earth_objects")
    val nearEarthObjects: Map<String, List<NetworkAsteroid>>,
    @SerializedName("links")
    val pageKeys: NetworkLinks
)

data class NetworkAsteroid(
    val links: NetworkAsteroidLinks,
    val id: String,
    @SerializedName("neo_reference_id")
    val neoReferenceId: String,
    val name: String,
    @SerializedName("nasa_jpl_url")
    val nasaJplUrl: String,
    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitudeH: Double,
    @SerializedName("estimated_diameter")
    val estimatedDiameter: NetworkEstimatedDiameter,
    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,
    @SerializedName("close_approach_data")
    val closeApproachData: List<NetworkCloseApproachData>,
    @SerializedName("is_sentry_object")
    val isSentryObject: Boolean
)

data class NetworkAsteroidLinks(
    val self: String
)

data class NetworkEstimatedDiameter(
    val kilometers: NetworkDiameter,
    val meters: NetworkDiameter,
    val miles: NetworkDiameter,
    val feet: NetworkDiameter
)

data class NetworkDiameter(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double,
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double
)

data class NetworkCloseApproachData(
    @SerializedName("close_approach_date")
    val closeApproachDate: String,
    @SerializedName("close_approach_date_full")
    val closeApproachDateFull: String,
    @SerializedName("epoch_date_close_approach")
    val epochDateCloseApproach: Long,
    @SerializedName("relative_velocity")
    val relativeVelocity: NetworkRelativeVelocity,
    @SerializedName("miss_distance")
    val missDistance: NetworkMissDistance,
    @SerializedName("orbiting_body")
    val orbitingBody: String
)

data class NetworkRelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: String,
    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: String,
    @SerializedName("miles_per_hour")
    val milesPerHour: String
)

data class NetworkMissDistance(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

data class NetworkLinks(
    val next: String,
    val prev: String,
    val self: String
)
