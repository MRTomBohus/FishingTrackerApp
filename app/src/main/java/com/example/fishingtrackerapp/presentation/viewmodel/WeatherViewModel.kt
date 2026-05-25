package com.example.fishingtrackerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishingtrackerapp.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel that loads weather data for selected fishing spots.
 */
class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    private var lastLoadedLatitude: Double? = null
    private var lastLoadedLongitude: Double? = null

    fun loadWeather(
        latitude: Double,
        longitude: Double,
        forceRefresh: Boolean = false
    ) {
        val sameSpotAlreadyLoaded =
            lastLoadedLatitude == latitude &&
                    lastLoadedLongitude == longitude &&
                    _weatherState.value is WeatherUiState.Success

        if (sameSpotAlreadyLoaded && !forceRefresh) {
            return
        }

        lastLoadedLatitude = latitude
        lastLoadedLongitude = longitude
        _weatherState.value = WeatherUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherInfo = repository.getWeather(
                    latitude = latitude,
                    longitude = longitude
                )

                _weatherState.value = WeatherUiState.Success(weatherInfo)
            } catch (_: Exception) {
                _weatherState.value = WeatherUiState.Error(
                    message = "Weather data could not be loaded."
                )
            }
        }
    }
}