package com.hp.weatherapp.domain.models

import androidx.annotation.Keep

@Keep
data class LocationSearchResult(
    val name: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double
)
