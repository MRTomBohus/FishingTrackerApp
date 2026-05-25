package com.example.fishingtrackerapp.data.remote

import com.example.fishingtrackerapp.domain.weather.FishingCondition
import com.example.fishingtrackerapp.domain.weather.WeatherInfo
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Loads weather information from Open-Meteo API.
 */
class WeatherRemoteDataSource {

    fun getWeather(latitude: Double, longitude: Double): WeatherInfo {
        val url = URL(
            "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=$latitude" +
                    "&longitude=$longitude" +
                    "&current=temperature_2m,precipitation,wind_speed_10m" +
                    "&timezone=auto"
        )

        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 8000
            connection.readTimeout = 8000

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(response)
            val current = json.getJSONObject("current")

            val temperature = current.getDouble("temperature_2m")
            val precipitation = current.getDouble("precipitation")
            val windSpeed = current.getDouble("wind_speed_10m")

            val condition = evaluateFishingCondition(
                windSpeed = windSpeed,
                precipitation = precipitation
            )

            WeatherInfo(
                temperature = temperature,
                windSpeed = windSpeed,
                precipitation = precipitation,
                condition = condition
            )
        } finally {
            connection.disconnect()
        }
    }

    private fun evaluateFishingCondition(
        windSpeed: Double,
        precipitation: Double
    ): FishingCondition {
        return when {
            windSpeed <= 15.0 && precipitation <= 0.5 -> FishingCondition.GOOD
            windSpeed <= 30.0 && precipitation <= 3.0 -> FishingCondition.AVERAGE
            else -> FishingCondition.BAD
        }
    }
}