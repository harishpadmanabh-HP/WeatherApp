package com.hp.weatherapp.data.repositoryImpl

import com.hp.weatherapp.data.DTO.toCurrentWeather
import com.hp.weatherapp.data.remote.WeatherApi
import com.hp.weatherapp.domain.models.CurrentWeather
import com.hp.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): CurrentWeather = weatherApi.getCurrentWeather(latitude, longitude).toCurrentWeather()
}