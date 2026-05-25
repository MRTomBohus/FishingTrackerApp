package com.example.fishingtrackerapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fishingtrackerapp.domain.model.Catch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.fishingtrackerapp.presentation.viewmodel.CatchViewModel
import androidx.compose.ui.res.stringResource
import com.example.fishingtrackerapp.R
import androidx.compose.material3.Scaffold
import com.example.fishingtrackerapp.presentation.components.FishingTrackerTopBar
import com.example.fishingtrackerapp.presentation.components.FishingTrackerCard
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CatchListScreen(
    viewModel: CatchViewModel,
    onBackClick: () -> Unit,
    onCatchClick: (Int) -> Unit
) {
    val catches by viewModel.catches.collectAsState()

    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.my_catches),
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (catches.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.no_catches_saved),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = stringResource(R.string.add_first_catch_hint),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(catches) { fishCatch ->
                    CatchListItem(
                        fishCatch = fishCatch,
                        onClick = {
                            onCatchClick(fishCatch.id)
                        }
                    )
                }
            }
        }
    }
    }
}

@Composable
private fun CatchListItem(
    fishCatch: Catch,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val statusText = if (fishCatch.released) {
        stringResource(R.string.released_status)
    } else {
        stringResource(R.string.kept_status)
    }

    FishingTrackerCard(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.SetMeal,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = fishCatch.fishType,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = fishCatch.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CatchBadge(
                    text = stringResource(R.string.kg_value, fishCatch.weight),
                    modifier = Modifier.weight(1f)
                )

                CatchBadge(
                    text = stringResource(R.string.cm_value, fishCatch.length),
                    modifier = Modifier.weight(1f)
                )
            }

            CatchBadge(
                text = stringResource(R.string.bait_badge, fishCatch.bait),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CatchBadge(
                    text = dateFormat.format(Date(fishCatch.date)),
                    modifier = Modifier.weight(1f)
                )

                CatchBadge(
                    text = statusText,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CatchBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.SemiBold
    )
}