package com.hp.weatherapp.domain.repository

import com.hp.weatherapp.domain.models.CurrentLocation
import com.hp.weatherapp.domain.models.LocationSearchResult
import com.hp.weatherapp.presentation.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCurrentLocation(highAccuracy: Boolean = true): Flow<CurrentLocation>
    suspend fun searchLocations(query: String): List<LocationSearchResult>
}