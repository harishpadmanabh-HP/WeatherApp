package com.hp.weatherapp.domain.repository

import com.hp.weatherapp.domain.models.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): CurrentWeather

}