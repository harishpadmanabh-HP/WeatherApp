package com.hp.weatherapp.data.remote

import com.hp.weatherapp.BuildConfig
import com.hp.weatherapp.data.DTO.WeatherResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String = "metric" // For Celsius
    ): WeatherResultDTO
}