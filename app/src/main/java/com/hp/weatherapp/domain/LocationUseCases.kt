package com.hp.weatherapp.domain

import com.hp.weatherapp.domain.models.CurrentLocation
import com.hp.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationUseCases(
    private val repository: LocationRepository
) {
     fun getCurrentLocation(highAccuracy: Boolean = true): Flow<CurrentLocation> {
        return repository.getCurrentLocation(highAccuracy)
    }

    suspend fun searchLocation(query: String) = repository.searchLocations(query)
}

