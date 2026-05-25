package com.example.fishingtrackerapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.fishingtrackerapp.presentation.viewmodel.CatchViewModel
import androidx.compose.ui.res.stringResource
import com.example.fishingtrackerapp.R
import androidx.compose.material3.Scaffold
import com.example.fishingtrackerapp.presentation.components.FishingTrackerTopBar
import com.example.fishingtrackerapp.presentation.components.FishingTrackerCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun StatisticsScreen(
    viewModel: CatchViewModel,
    onBackClick: () -> Unit
) {
    val catches by viewModel.catches.collectAsState()

    val totalCatches = catches.size
    val biggestCatch = catches.maxByOrNull { it.weight }
    val longestCatch = catches.maxByOrNull { it.length }
    val averageWeight = if (catches.isNotEmpty()) {
        catches.map { it.weight }.average()
    } else {
        0.0
    }

    val averageLength = if (catches.isNotEmpty()) {
        catches.map { it.length }.average()
    } else {
        0.0
    }
    val releasedCatches = catches.count { it.released }
    val keptCatches = catches.count { !it.released }
    val releaseRate = if (totalCatches > 0) {
        releasedCatches.toDouble() / totalCatches.toDouble() * 100.0
    } else {
        0.0
    }

    val mostCommonFish = catches
        .groupingBy { it.fishType }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key ?: stringResource(R.string.no_catches)

    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.statistics),
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    title = stringResource(R.string.total_catches),
                    value = totalCatches.toString(),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Assessment,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                StatisticCard(
                    title = stringResource(R.string.average_weight),
                    value = stringResource(R.string.kg_value, averageWeight),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Scale,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    title = stringResource(R.string.average_length),
                    value = stringResource(R.string.cm_value, averageLength),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Straighten,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                StatisticCard(
                    title = stringResource(R.string.most_common_fish),
                    value = mostCommonFish,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.SetMeal,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatisticCard(
                    title = stringResource(R.string.released_catches),
                    value = releasedCatches.toString(),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.SetMeal,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                StatisticCard(
                    title = stringResource(R.string.release_rate),
                    value = stringResource(R.string.percent_value, releaseRate),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Assessment,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            HighlightStatisticCard(
                title = stringResource(R.string.biggest_catch),
                value = biggestCatch?.let {
                    stringResource(R.string.catch_with_weight, it.fishType, it.weight)
                } ?: stringResource(R.string.no_catches)
            )

            HighlightStatisticCard(
                title = stringResource(R.string.longest_catch),
                value = longestCatch?.let {
                    stringResource(R.string.catch_with_length, it.fishType, it.length)
                } ?: stringResource(R.string.no_catches)
            )
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    FishingTrackerCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun HighlightStatisticCard(
    title: String,
    value: String
) {
    FishingTrackerCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}