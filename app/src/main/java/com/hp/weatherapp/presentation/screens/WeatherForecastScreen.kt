package com.hp.weatherapp.presentation.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import com.hp.weatherapp.presentation.ForeCastScreenState
import com.hp.weatherapp.presentation.MainViewModel
import com.hp.weatherapp.presentation.theme.White
import com.hp.weatherapp.presentation.theme.Yellow
import com.hp.weatherapp.presentation.utils.LoadingDialog

@Composable
fun WeatherForecastScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchedLocations by viewModel.searchedLocations.collectAsState()
    val selectedLatLng by viewModel.selectedLocationFromSearch.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentLocation()
    }

    LaunchedEffect(screenState) {
        if(screenState is ForeCastScreenState.OnCurrentLocationFetched){
            screenState.currentLocation?.let {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f),
                    durationMs = 500
                )
            }

        }
    }

    LaunchedEffect(selectedLatLng) {
        selectedLatLng?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f),
                durationMs = 1000
            )
        }
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
                    cursorColor = Yellow,
                ),
                label = {
                    Text(
                        text = "Search any location",
                        color = Yellow.copy(.5f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            AnimatedVisibility(
                visible = searchedLocations.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                LazyColumn {
                    items(searchedLocations) {
                        Text(
                            text = it.displayName,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .clickable {
                                    viewModel.onSelectedLocationFromSearch(it)
                                }
                        )
                    }
                }
            }

            GoogleMap(
                properties = MapProperties(
                    isMyLocationEnabled = true
                ),
                uiSettings = MapUiSettings(
                    compassEnabled = true,
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = true,
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
                    .scale(1f),
                cameraPositionState = cameraPositionState
            ) {

                screenState.currentLocation?.let{
                    Marker(
                        state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                        onClick = {
                            viewModel.onMarkerClicked(it.position.latitude,it.position.longitude)
                            true
                        }
                    )
                }
                selectedLatLng?.let {
                    Marker(
                        state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                        title = it.name
                    )
                }
            }
        }
    }
}