package dev.kobzar.preferences.model

import androidx.annotation.StringRes
import dev.kobzar.preferences.R
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesModel(
    val diameterUnits: DiameterUnit,
    val relativeVelocityUnits: RelativeVelocityUnit,
    val missDistanceUnits: MissDistanceUnit
)

@Serializable
enum class DiameterUnit(@StringRes val unit: Int) {
    KILOMETER(R.string.unit_km),
    METER(R.string.unit_meters),
    MILE(R.string.unit_miles),
    FEET(R.string.unit_feets)
}

@Serializable
enum class RelativeVelocityUnit(@StringRes val unit: Int) {
    KM_S(R.string.unit_speed_km_s),
    KM_H(R.string.unit_speed_km_h),
    MILE_H(R.string.unit_speed_mph)
}

@Serializable
enum class MissDistanceUnit(@StringRes val unit: Int) {
    KILOMETER(R.string.unit_km),
    MILE(R.string.unit_miles),
    LUNAR(R.string.unit_lunar),
    ASTRONOMICAL(R.string.unit_astronomical)
}
