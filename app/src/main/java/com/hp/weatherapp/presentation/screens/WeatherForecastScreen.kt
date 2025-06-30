package com.hp.weatherapp.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hp.weatherapp.R
import com.hp.weatherapp.presentation.MainViewModel
import com.hp.weatherapp.presentation.theme.DarkBlue
import com.hp.weatherapp.presentation.theme.Purple
import com.hp.weatherapp.presentation.theme.White
import com.hp.weatherapp.presentation.theme.Yellow
import com.hp.weatherapp.presentation.utils.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastScreen(
    hasLocationPermissions: Boolean,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val searchedLocations by viewModel.searchedLocations.collectAsStateWithLifecycle()
    val selectedLatLng by viewModel.selectedLocationFromSearch.collectAsStateWithLifecycle()
    val cameraPositionState = rememberCameraPositionState()
    val currentDeviceLocation by viewModel.currentDeviceLocation.collectAsStateWithLifecycle()
    val fetchedWeather by viewModel.fetchedWeather.collectAsStateWithLifecycle()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(hasLocationPermissions) {
        if (hasLocationPermissions)
            viewModel.fetchCurrentLocation()
    }

    LaunchedEffect(currentDeviceLocation) {
        currentDeviceLocation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 16f),
                durationMs = 500
            )
        }
    }

    LaunchedEffect(selectedLatLng) {
        selectedLatLng?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 16f),
                durationMs = 1000
            )
        }
    }

    LaunchedEffect(fetchedWeather) {
        if (fetchedWeather != null)
            isSheetOpen = true
    }

    Box(
        modifier
            .fillMaxSize()
    ) {
        LoadingDialog(isVisible = screenState.isLoading)

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.weather_forecast),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    viewModel.onSearchTextChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = White,
                    unfocusedTextColor = White.copy(.5f),
                    cursorColor = Yellow
                ),
                label = {
                    Text(
                        text = stringResource(R.string.search_any_location),
                        color = Yellow.copy(.5f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            AnimatedVisibility(
                visible = searchedLocations.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Purple, shape = RoundedCornerShape(12.dp))
                    .height(200.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(12.dp)) {
                    items(searchedLocations) {
                        Text(
                            text = it.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable {
                                    viewModel.onSelectedLocationFromSearch(it)
                                    keyboardController?.hide()
                                }
                        )
                    }
                }
            }

            GoogleMap(
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermissions
                ),
                uiSettings = MapUiSettings(
                    compassEnabled = true,
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = hasLocationPermissions,
                    mapToolbarEnabled = true,
                    indoorLevelPickerEnabled = true,
                    rotationGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    tiltGesturesEnabled = true,
                    zoomGesturesEnabled = true
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .scale(1f)
                    .clip(RoundedCornerShape(18.dp)),
                cameraPositionState = cameraPositionState
            ) {

                currentDeviceLocation?.let { location ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                location.latitude,
                                location.longitude
                            )
                        ),
                        onClick = {
                            viewModel.onMarkerClicked(location.latitude, location.longitude)
                            true
                        }
                    )
                }
                selectedLatLng?.let { location ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                location.latitude,
                                location.longitude
                            )
                        ),
                        title = location.name,
                        onClick = {
                            viewModel.onMarkerClicked(location.latitude, location.longitude)
                            false
                        }
                    )
                }
            }
        }

        if (isSheetOpen) {
            fetchedWeather?.let { weatherInfo ->
                ModalBottomSheet(
                    containerColor = DarkBlue,
                    onDismissRequest = {
                        isSheetOpen = false
                        viewModel.clearFetchedWeather()
                    }
                ) {
                    WeatherInfoColumn(info = weatherInfo)
                }
            }
        }
    }
}