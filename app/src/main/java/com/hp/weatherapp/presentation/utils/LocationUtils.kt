package com.hp.weatherapp.presentation.utils

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.hp.weatherapp.domain.models.CurrentLocation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("MissingPermission")
fun getCurrentLocationFlow(
    fusedLocationProviderClient: FusedLocationProviderClient,
    highAccuracy: Boolean = true
): Flow<CurrentLocation> = callbackFlow {
    val priority = if (highAccuracy) {
        Priority.PRIORITY_HIGH_ACCURACY
    } else {
        Priority.PRIORITY_BALANCED_POWER_ACCURACY
    }

    val cancellationTokenSource = CancellationTokenSource()

    fusedLocationProviderClient
        .getCurrentLocation(priority, cancellationTokenSource.token)
        .addOnSuccessListener { location ->
            if (location != null) {
                trySend(CurrentLocation(location.latitude, location.longitude)).isSuccess
                close() // Close the flow after emitting one value
            } else {
                close(Exception("Location is null"))
            }
        }
        .addOnFailureListener { e ->
            close(e)
        }

    // Cancel the location request if the flow collector is cancelled
    awaitClose {
        cancellationTokenSource.cancel()
    }
}