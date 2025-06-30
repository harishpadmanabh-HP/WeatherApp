package com.hp.weatherapp.presentation

import androidx.annotation.Keep
import androidx.compose.runtime.mutableStateOf
import com.hp.weatherapp.domain.models.CurrentLocation
import com.hp.weatherapp.domain.models.CurrentWeather
import com.hp.weatherapp.domain.models.LocationSearchResult

sealed class ForeCastScreenState {
    open val errorMessage: String? = null
    open val isLoading: Boolean = false

    object Loading : ForeCastScreenState() {
        override val isLoading = true
    }

    data class OnError(val message: String) : ForeCastScreenState() {
        override val errorMessage = message
    }

    object StartUp : ForeCastScreenState()
    object Idle : ForeCastScreenState()



}

