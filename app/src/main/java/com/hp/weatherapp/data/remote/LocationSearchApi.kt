package com.hp.weatherapp.data.remote

import com.hp.weatherapp.data.DTO.LocationSearchResultDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Headers

interface LocationSearchApi {

    @GET("search")
    @Headers("User-Agent: MyApp/1.0")
    suspend fun searchLocation(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 10
    ): List<LocationSearchResultDTO>

}