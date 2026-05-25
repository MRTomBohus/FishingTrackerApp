package com.example.fishingtrackerapp.domain.weather

/**
 * Represents simplified weather information used in the Map & Spots screen.
 */
data class WeatherInfo(
    val temperature: Double,
    val windSpeed: Double,
    val precipitation: Double,
    val condition: FishingCondition
)

/**
 * Represents evaluated fishing conditions based on current weather.
 */
enum class FishingCondition {
    GOOD,
    AVERAGE,
    BAD
}