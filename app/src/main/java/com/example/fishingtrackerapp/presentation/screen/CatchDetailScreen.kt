package com.example.fishingtrackerapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fishingtrackerapp.presentation.viewmodel.CatchViewModel
import androidx.compose.ui.res.stringResource
import com.example.fishingtrackerapp.R
import androidx.compose.material3.Scaffold
import com.example.fishingtrackerapp.presentation.components.FishingTrackerTopBar
import com.example.fishingtrackerapp.presentation.components.FishingTrackerCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun CatchDetailScreen(
    catchId: Int,
    viewModel: CatchViewModel,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val fishCatch = viewModel.getCatchById(catchId)

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.catch_detail),
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
            if (fishCatch == null) {
                Text(text = stringResource(R.string.catch_not_found))
            } else {
                FishingTrackerCard {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.SetMeal,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }

                            Column(
                                modifier = Modifier.padding(start = 14.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.fish_type_label),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Text(
                                    text = fishCatch.fishType,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        DetailRow(
                            label = stringResource(R.string.weight_kg),
                            value = stringResource(R.string.kg_value, fishCatch.weight)
                        )

                        DetailRow(
                            label = stringResource(R.string.length_cm),
                            value = stringResource(R.string.cm_value, fishCatch.length)
                        )

                        DetailRow(
                            label = stringResource(R.string.location),
                            value = fishCatch.location
                        )

                        DetailRow(
                            label = stringResource(R.string.bait),
                            value = fishCatch.bait
                        )

                        DetailRow(
                            label = stringResource(R.string.date_label_full),
                            value = dateFormat.format(Date(fishCatch.date))
                        )

                        DetailRow(
                            label = stringResource(R.string.status_label),
                            value = if (fishCatch.released) {
                                stringResource(R.string.released_status)
                            } else {
                                stringResource(R.string.kept_status)
                            }
                        )

                        DetailRow(
                            label = stringResource(R.string.note_label),
                            value = fishCatch.note.ifBlank {
                                stringResource(R.string.no_note)
                            }
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.edit_catch))
            }

            OutlinedButton(
                onClick = {
                    viewModel.deleteCatch(catchId)
                    onDeleteClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.delete_catch))
            }

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.back))
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}