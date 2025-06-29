package com.hp.weatherapp.data.repositoryImpl

import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.hp.weatherapp.data.DTO.toLocationSearchResult
import com.hp.weatherapp.data.remote.LocationSearchApi
import com.hp.weatherapp.domain.models.CurrentLocation
import com.hp.weatherapp.domain.models.LocationSearchResult
import com.hp.weatherapp.domain.repository.LocationRepository
import com.hp.weatherapp.presentation.utils.getCurrentLocationFlow
import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationSearchApi: LocationSearchApi
) : LocationRepository {
    override fun getCurrentLocation(highAccuracy: Boolean): Flow<CurrentLocation> =
        getCurrentLocationFlow(
            fusedLocationProviderClient = fusedLocationProviderClient
        )

    override suspend fun searchLocations(query: String): List<LocationSearchResult> {
        return try {
            val response = locationSearchApi.searchLocation(query)
            response.map {
                it.toLocationSearchResult()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("SEARCH_API_ERROR", e.toString())
            emptyList<LocationSearchResult>()
        }
    }

}