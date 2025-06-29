package com.hp.weatherapp.data.DTO


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.hp.weatherapp.domain.models.LocationSearchResult

@Keep
data class LocationSearchResultDTO(
    @SerializedName("addresstype")
    val addresstype: String,
    @SerializedName("boundingbox")
    val boundingbox: List<String>,
    @SerializedName("class")
    val classX: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("importance")
    val importance: Double,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("licence")
    val licence: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("osm_id")
    val osmId: Long,
    @SerializedName("osm_type")
    val osmType: String,
    @SerializedName("place_id")
    val placeId: Int,
    @SerializedName("place_rank")
    val placeRank: Int,
    @SerializedName("type")
    val type: String
)

fun LocationSearchResultDTO.toLocationSearchResult() = LocationSearchResult(
    name = name,
    displayName=displayName,
    latitude = lat.toDouble(),
    longitude = lon.toDouble()
)