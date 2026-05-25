package com.example.fishingtrackerapp.presentation.viewmodel

import com.example.fishingtrackerapp.domain.weather.WeatherInfo

/**
 * Represents UI states used while loading weather data.
 */
sealed interface WeatherUiState {
    data object Idle : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val weatherInfo: WeatherInfo) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}