package com.hp.weatherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hp.weatherapp.BuildConfig
import com.hp.weatherapp.data.remote.LocationSearchApi
import com.hp.weatherapp.data.remote.WeatherApi
import com.hp.weatherapp.data.repositoryImpl.LocationRepositoryImpl
import com.hp.weatherapp.data.repositoryImpl.WeatherRepositoryImpl
import com.hp.weatherapp.domain.LocationUseCases
import com.hp.weatherapp.domain.WeatherUseCases
import com.hp.weatherapp.domain.repository.LocationRepository
import com.hp.weatherapp.domain.repository.WeatherRepository
import com.hp.weatherapp.presentation.utils.LOCATION_SEARCH_BASE_URL
import com.hp.weatherapp.presentation.utils.WEATHER_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationSearchRetrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .also {
            if (BuildConfig.DEBUG) {
                it.addInterceptor(httpLoggingInterceptor)
            }
        }.readTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(5, TimeUnit.MINUTES).build()

    @Provides
    @Singleton
    @WeatherRetrofit
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @LocationSearchRetrofit
    fun provideLocationSearchRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LOCATION_SEARCH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLocationSearchApi(@LocationSearchRetrofit retrofit: Retrofit): LocationSearchApi =
        retrofit.create(LocationSearchApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApi(@WeatherRetrofit retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideLocationRepository(
        client: FusedLocationProviderClient,
        locationSearchApi: LocationSearchApi
    ): LocationRepository = LocationRepositoryImpl(client, locationSearchApi)

    @Provides
    fun provideLocationUseCases(repository: LocationRepository) =
        LocationUseCases(repository)

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ): WeatherRepository = WeatherRepositoryImpl(weatherApi)

    @Provides
    fun providesWeatherUseCase(
        weatherRepository: WeatherRepository
    ) = WeatherUseCases(weatherRepository)
}