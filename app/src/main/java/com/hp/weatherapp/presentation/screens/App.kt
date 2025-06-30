package com.hp.weatherapp.presentation.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hp.weatherapp.presentation.MainViewModel
import com.hp.weatherapp.presentation.utils.RequestLocationPermission

@Composable
fun App(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier)
{
    var hasPermissions by remember { mutableStateOf(false) }
    var showPermissionDeniedDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current


    RequestLocationPermission(
        onPermissionDenied = {
            showPermissionDeniedDialog = true
            hasPermissions = false
        },
        onPermissionGranted = {
            hasPermissions = true
            showPermissionDeniedDialog = false
        },
        onPermissionsRevoked = {
            showPermissionDeniedDialog = true
            hasPermissions = false
        }
    )

    WeatherForecastScreen(
        hasLocationPermissions = hasPermissions,
        paddingValues=paddingValues,
        viewModel=viewModel
    )

    if (showPermissionDeniedDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDeniedDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog = false
                    // Open app settings
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog = false
                }) {
                    Text("Cancel")
                }
            },
            title = { Text("Permission Required") },
            text = {
                Text("Location permission is required to fetch your current location. Please enable it in settings.")
            }
        )
    }
}