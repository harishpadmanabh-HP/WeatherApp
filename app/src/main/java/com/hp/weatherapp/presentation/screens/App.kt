package com.hp.weatherapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hp.weatherapp.presentation.MainViewModel
import com.hp.weatherapp.presentation.permissions.RequestLocationPermission

@Composable
fun App(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier)
{
    RequestLocationPermission(
        onPermissionDenied = {
            Log.i("Permissions", "Denied")
        },
        onPermissionGranted = {
            Log.i("Permissions", "Granted")

        },
        onPermissionsRevoked = {
            Log.i("Permissions", "Revoked")
        }
    )

    WeatherForecastScreen(
        paddingValues=paddingValues,
        viewModel=viewModel
    )
}