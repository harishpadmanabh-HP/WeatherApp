package com.hp.weatherapp.domain

import com.hp.weatherapp.domain.repository.WeatherRepository
import com.hp.weatherapp.presentation.utils.NetworkResult
import kotlinx.coroutines.flow.flow

class WeatherUseCases(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getWeather(latitude: Double, longitude: Double) = flow {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(weatherRepository.getWeather(latitude, latitude)))
        } catch (e: Exception) {
            emit(NetworkResult.Failure(e.toString()))
        }
    }
}