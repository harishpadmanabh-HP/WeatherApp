package com.hp.weatherapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary=DarkBlue,
    surface = DarkBlue,
    background = DarkBlue
)


@Composable
fun WeatherAppTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}