package com.hp.weatherapp.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hp.weatherapp.domain.LocationUseCases
import com.hp.weatherapp.domain.WeatherUseCases
import com.hp.weatherapp.domain.models.CurrentLocation
import com.hp.weatherapp.domain.models.CurrentWeather
import com.hp.weatherapp.domain.models.LocationSearchResult
import com.hp.weatherapp.presentation.utils.doIfFailure
import com.hp.weatherapp.presentation.utils.doIfLoading
import com.hp.weatherapp.presentation.utils.doIfSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
    private val weatherUseCases: WeatherUseCases
) : ViewModel() {

    private val _screenState = MutableStateFlow<ForeCastScreenState>(ForeCastScreenState.StartUp)
    val screenState: StateFlow<ForeCastScreenState> = _screenState.asStateFlow()

    private val _searchText = MutableStateFlow(TextFieldValue(""))
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchedLocations = MutableStateFlow(mutableListOf<LocationSearchResult>())

    @OptIn(FlowPreview::class)
    val searchedLocations = searchText
        .debounce { 300L }
        .onEach { _isSearching.update { true } }
        .combine(_searchedLocations) { query, locations ->
            if (query.text.isBlank()) {
                mutableListOf<LocationSearchResult>()
            } else {
                locationUseCases.searchLocation(query.text)
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchedLocations.value
        )

    fun onSearchTextChange(text: TextFieldValue) {
        _searchText.value = text
    }

    private val _selectedLocationFromSearch = MutableStateFlow<LocationSearchResult?>(null)
    val selectedLocationFromSearch: StateFlow<LocationSearchResult?> =
        _selectedLocationFromSearch.asStateFlow()

    fun onSelectedLocationFromSearch(locationSearchResult: LocationSearchResult) {
        _searchText.value = TextFieldValue("")
        _searchedLocations.value = mutableListOf()
        _selectedLocationFromSearch.value = locationSearchResult
    }


    private val _currentDeviceLocation = MutableStateFlow<CurrentLocation?>(null)
    val currentDeviceLocation = _currentDeviceLocation.asStateFlow()
    fun fetchCurrentLocation(highAccuracy: Boolean = true) {
        viewModelScope.launch {
            locationUseCases.getCurrentLocation(highAccuracy)
                .onStart {
                    _screenState.value = ForeCastScreenState.Loading
                }
                .catch {
                    _screenState.value =
                        ForeCastScreenState.OnError("Fetching device location failed due to ${it.localizedMessage}")
                }
                .collect { location ->
                    _currentDeviceLocation.value = location
                    _screenState.value = ForeCastScreenState.Idle
                }
        }
    }

    private val _fetchedWeather = MutableStateFlow<CurrentWeather?>(null)
    val fetchedWeather = _fetchedWeather.asStateFlow()

    fun onMarkerClicked(latitude: Double, longitude: Double, name: String = "") {
        viewModelScope.launch {
            weatherUseCases.getWeather(latitude, longitude).collectLatest { result ->
                result.apply {
                    doIfLoading {
                        _screenState.value = ForeCastScreenState.Loading
                    }
                    doIfSuccess {
                        _screenState.value = ForeCastScreenState.Idle
                        _fetchedWeather.value = it
                    }
                    doIfFailure {
                        _screenState.value =
                            ForeCastScreenState.OnError("Fetching weather failed due to $it")
                    }
                }
            }
        }
    }

    fun clearFetchedWeather(){
        _fetchedWeather.value = null
    }

}