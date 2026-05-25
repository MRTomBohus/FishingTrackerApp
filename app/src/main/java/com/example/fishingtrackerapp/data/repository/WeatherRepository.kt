package com.example.fishingtrackerapp.data.repository

import com.example.fishingtrackerapp.data.remote.WeatherRemoteDataSource
import com.example.fishingtrackerapp.domain.weather.WeatherInfo

/**
 * Repository responsible for loading weather information for fishing spots.
 */
class WeatherRepository(
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource()
) {

    fun getWeather(latitude: Double, longitude: Double): WeatherInfo {
        return remoteDataSource.getWeather(latitude, longitude)
    }
}