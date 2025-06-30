package com.hp.weatherapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hp.weatherapp.domain.models.CurrentWeather
import com.hp.weatherapp.presentation.theme.Orange
import com.hp.weatherapp.presentation.theme.Yellow

@Composable
fun WeatherInfoColumn(
    info: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                append(info.temperature) // temperature
                withStyle(style = SpanStyle(color = Yellow)) {
                    append("°C") // colored separately
                }
            },
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = info.feelsLike,
            style = MaterialTheme.typography.displaySmall,
            modifier= Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = info.weatherDescription,
            style = MaterialTheme.typography.displayLarge,
            color = Yellow
        )

        AsyncImage(
            model = info.weatherIconUrl,
            contentDescription = "Weather icon",
            modifier = modifier.size(100.dp)
        )

        Text(
            text = "Atmospheric Conditions",
            style = MaterialTheme.typography.displayMedium,
            modifier= Modifier.fillMaxWidth().padding(vertical = 14.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "\uD83D\uDCA7 Humidity — ${info.humidity}",
            style = MaterialTheme.typography.displaySmall,
            modifier= Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "\uD83C\uDF88 Pressure — ${info.pressure}",
            style = MaterialTheme.typography.displaySmall,
            modifier= Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "\uD83C\uDF2B\uFE0F Visibility — ${info.visibility}",
            style = MaterialTheme.typography.displaySmall,
            modifier= Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "\uD83C\uDF43 Speed — ${info.wind}",
            style = MaterialTheme.typography.displaySmall,
            modifier= Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "☁\uFE0F Cloudiness — ${info.cloudiness}",
            style = MaterialTheme.typography.displaySmall,
            modifier= Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Start
        )

    }
}