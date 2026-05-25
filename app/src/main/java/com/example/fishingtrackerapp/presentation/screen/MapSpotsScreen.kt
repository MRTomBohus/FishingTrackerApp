package com.example.fishingtrackerapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fishingtrackerapp.domain.model.FishingSpot
import androidx.compose.ui.res.stringResource
import com.example.fishingtrackerapp.R
import androidx.compose.material3.Scaffold
import com.example.fishingtrackerapp.presentation.components.FishingTrackerTopBar
import com.example.fishingtrackerapp.presentation.viewmodel.FishingSpotViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.fishingtrackerapp.presentation.components.FishingTrackerCard
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.fishingtrackerapp.domain.weather.FishingCondition
import com.example.fishingtrackerapp.domain.weather.WeatherInfo
import com.example.fishingtrackerapp.presentation.viewmodel.WeatherUiState
import com.example.fishingtrackerapp.presentation.viewmodel.WeatherViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf

@Composable
fun MapSpotsScreen(
    viewModel: FishingSpotViewModel,
    weatherViewModel: WeatherViewModel,
    onBackClick: () -> Unit
) {
    val spots = viewModel.fishingSpots

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val filteredSpots = spots.filter { spot ->
        spot.name.contains(searchQuery, ignoreCase = true) ||
                spot.type.contains(searchQuery, ignoreCase = true)
    }

    var selectedSpotId by rememberSaveable {
        mutableIntStateOf(spots.first().id)
    }

    val selectedSpot = spots.firstOrNull { it.id == selectedSpotId }

    val weatherState by weatherViewModel.weatherState.collectAsState()

    LaunchedEffect(selectedSpotId) {
        selectedSpot?.let { spot ->
            weatherViewModel.loadWeather(
                latitude = spot.latitude,
                longitude = spot.longitude,
            )
        }
    }

    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.map_spots),
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.mso_zilina_spots),
                style = MaterialTheme.typography.bodyLarge
            )

            selectedSpot?.let { spot ->
                FishingTrackerCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = spot.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(text = spot.type)
                        Text(text = spot.description)
                    }
                }
                selectedSpot?.let { spot ->
                    WeatherCard(
                        weatherState = weatherState,
                        onRefreshClick = {
                            weatherViewModel.loadWeather(
                                latitude = spot.latitude,
                                longitude = spot.longitude,
                                forceRefresh = true
                            )
                        }
                    )
                }
            }

            Text(
                text = stringResource(R.string.available_spots),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = {
                    Text(stringResource(R.string.search_spots))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (filteredSpots.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_spots_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                filteredSpots.forEach { spot ->
                    FishingSpotItem(
                        spot = spot,
                        onClick = {
                            selectedSpotId = spot.id
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FishingSpotItem(
    spot: FishingSpot,
    onClick: () -> Unit
) {
    FishingTrackerCard(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = spot.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = spot.type)
        }
    }
}

@Composable
private fun WeatherCard(
    weatherState: WeatherUiState,
    onRefreshClick: () -> Unit
) {
    FishingTrackerCard {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.weather_for_spot),
                style = MaterialTheme.typography.titleLarge
            )

            when (weatherState) {
                WeatherUiState.Idle -> {
                    Text(text = stringResource(R.string.weather_loading))
                }

                WeatherUiState.Loading -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()

                        Text(text = stringResource(R.string.weather_loading))
                    }
                }

                is WeatherUiState.Error -> {
                    Text(
                        text = stringResource(R.string.weather_error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = stringResource(R.string.weather_error_hint),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                is WeatherUiState.Success -> {
                    WeatherContent(weatherInfo = weatherState.weatherInfo)
                }
            }
            OutlinedButton(
                onClick = onRefreshClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.refresh_weather))
            }
        }
    }
}

@Composable
private fun WeatherContent(
    weatherInfo: WeatherInfo
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WeatherRow(
            label = stringResource(R.string.temperature),
            value = stringResource(R.string.temperature_value, weatherInfo.temperature)
        )

        WeatherRow(
            label = stringResource(R.string.wind_speed),
            value = stringResource(R.string.wind_value, weatherInfo.windSpeed)
        )

        WeatherRow(
            label = stringResource(R.string.precipitation),
            value = stringResource(R.string.precipitation_value, weatherInfo.precipitation)
        )

        WeatherRow(
            label = stringResource(R.string.fishing_condition),
            value = weatherInfo.condition.toDisplayText()
        )

        Text(
            text = weatherInfo.condition.toRecommendationText(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun WeatherRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun FishingCondition.toDisplayText(): String {
    return when (this) {
        FishingCondition.GOOD -> stringResource(R.string.good_condition)
        FishingCondition.AVERAGE -> stringResource(R.string.average_condition)
        FishingCondition.BAD -> stringResource(R.string.bad_condition)
    }
}

@Composable
private fun FishingCondition.toRecommendationText(): String {
    return when (this) {
        FishingCondition.GOOD -> stringResource(R.string.recommendation_good)
        FishingCondition.AVERAGE -> stringResource(R.string.recommendation_average)
        FishingCondition.BAD -> stringResource(R.string.recommendation_bad)
    }
}