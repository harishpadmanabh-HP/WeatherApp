package com.hp.weatherapp.domain.models

import androidx.annotation.Keep

data class CurrentWeather(
    val location: String,          // "Vazhuthacaud, IN"
    val temperature: String,       // "26.8°C"
    val feelsLike: String,         // "Feels like 29.6°C"
    val tempRange: String,         // "Min 26.8°C / Max 26.8°C"
    val pressure: String,          // "1009 hPa"
    val humidity: String,          // "83%"
    val visibility: String,        // "4 km"
    val cloudiness: String,        // "40%"

    val wind: String,              // "2.6 m/s NW"
    val weatherMain: String,       // "Mist"
    val weatherDescription: String,// "mist"
    val weatherIconUrl: String,    // full icon url

    val sunrise: String,           // "06:43 AM"
    val sunset: String,            // "06:47 PM"
    val updatedAt: String          // "Updated at 03:21 PM"
)
