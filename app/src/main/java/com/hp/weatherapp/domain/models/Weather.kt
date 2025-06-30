package com.hp.weatherapp.domain.models

import androidx.annotation.Keep

data class CurrentWeather(
    val location: String,
    val temperature: String,
    val feelsLike: String,
    val tempRange: String,
    val pressure: String,
    val humidity: String,
    val visibility: String,
    val cloudiness: String,

    val wind: String,
    val weatherMain: String,
    val weatherDescription: String,
    val weatherIconUrl: String,

    val sunrise: String,
    val sunset: String,
    val updatedAt: String
)
