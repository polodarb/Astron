package dev.kobzar.platform.utils

object ConvertDiameterToKm {

    fun milesToKilometers(miles: Float): Float {
        return miles * 1.60934f
    }

    fun feetToKilometers(feet: Float): Float {
        return feet * 0.0003048f
    }

    fun metersToKilometers(meters: Float): Float {
        return meters * 0.001f
    }

}