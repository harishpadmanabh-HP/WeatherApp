package com.hp.weatherapp.domain.models

import androidx.annotation.Keep

@Keep
data class CurrentLocation(
    val latitude: Double,
    val longitude: Double,
)