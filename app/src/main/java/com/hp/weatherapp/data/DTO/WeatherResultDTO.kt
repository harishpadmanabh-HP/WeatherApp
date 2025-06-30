package com.hp.weatherapp.data.DTO


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.hp.weatherapp.domain.models.CurrentWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

@Keep
data class WeatherResultDTO(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) {
    @Keep
    data class Clouds(
        @SerializedName("all")
        val all: Int
    )

    @Keep
    data class Coord(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    @Keep
    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("grnd_level")
        val grndLevel: Int,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("sea_level")
        val seaLevel: Int,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
    )

    @Keep
    data class Sys(
        @SerializedName("country")
        val country: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("sunrise")
        val sunrise: Long,
        @SerializedName("sunset")
        val sunset: Long,
        @SerializedName("type")
        val type: Int
    )

    @Keep
    data class Weather(
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String
    )

    @Keep
    data class Wind(
        @SerializedName("deg")
        val deg: Int,
        @SerializedName("speed")
        val speed: Double
    )
}

fun String.toWeatherIconUrl(): String =
    "https://openweathermap.org/img/wn/${this.replace("n","d")}@2x.png"

fun Double.toCelsius(): String = "${"%.1f".format(this)}°C"
fun Double.toCelsiusWithoutUnit(): String = "%.1f".format(this)
fun Int.toPercent(): String = "$this%"
fun Int.toHpa(): String = "$this hPa"
fun Int.toKm(): String = "${this / 1000} km"
fun Long.toTimeString(offset: Int): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(Date((this + offset) * 1000L))
}

fun Int.toWindDirection(): String {
    val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
    val index = ((this % 360) / 45.0).roundToInt() % 8
    return directions[index]
}

fun WeatherResultDTO.toCurrentWeather(): CurrentWeather {
    val weatherInfo = weather.firstOrNull()
    val offset = timezone

    return CurrentWeather(
        location = "$name, ${sys.country}",
        temperature = main.temp.toCelsiusWithoutUnit(),
        feelsLike = "Feels like ${main.feelsLike.toCelsius()}",
        tempRange = "Min ${main.tempMin.toCelsius()} / Max ${main.tempMax.toCelsius()}",
        pressure = main.pressure.toHpa(),
        humidity = main.humidity.toPercent(),
        visibility = visibility.toKm(),
        cloudiness = clouds.all.toPercent(),

        wind = "${wind.speed} m/s ${wind.deg.toWindDirection()}",
        weatherMain = weatherInfo?.main.orEmpty(),
        weatherDescription = weatherInfo?.description.orEmpty(),
        weatherIconUrl = weatherInfo?.icon?.toWeatherIconUrl().orEmpty(),

        sunrise = sys.sunrise.toTimeString(offset),
        sunset = sys.sunset.toTimeString(offset),
        updatedAt = dt.toTimeString(offset)
    )
}