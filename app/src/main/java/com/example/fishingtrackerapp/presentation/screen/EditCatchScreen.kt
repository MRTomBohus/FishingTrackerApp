package com.example.fishingtrackerapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fishingtrackerapp.R
import com.example.fishingtrackerapp.presentation.components.FishingTrackerCard
import com.example.fishingtrackerapp.presentation.components.FishingTrackerTopBar
import com.example.fishingtrackerapp.presentation.viewmodel.CatchViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditCatchScreen(
    catchId: Int,
    viewModel: CatchViewModel,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val fishCatch = viewModel.getCatchById(catchId)

    val fillRequiredFieldsMessage = stringResource(R.string.fill_required_fields)
    val weightPositiveErrorMessage = stringResource(R.string.weight_positive_error)
    val lengthPositiveErrorMessage = stringResource(R.string.length_positive_error)
    val invalidDateMessage = stringResource(R.string.invalid_date)

    if (fishCatch == null) {
        Scaffold(
            topBar = {
                FishingTrackerTopBar(
                    title = stringResource(R.string.edit_catch),
                    showBackButton = true,
                    onBackClick = onBackClick
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.catch_not_found),
                    style = MaterialTheme.typography.bodyLarge
                )

                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.back))
                }
            }
        }

        return
    }

    var fishType by rememberSaveable { mutableStateOf(fishCatch.fishType) }
    var weight by rememberSaveable { mutableStateOf(fishCatch.weight.toString()) }
    var length by rememberSaveable { mutableStateOf(fishCatch.length.toString()) }
    var location by rememberSaveable { mutableStateOf(fishCatch.location) }
    var bait by rememberSaveable { mutableStateOf(fishCatch.bait) }
    var dateText by rememberSaveable { mutableStateOf(dateFormat.format(Date(fishCatch.date))) }
    var released by rememberSaveable { mutableStateOf(fishCatch.released) }
    var note by rememberSaveable { mutableStateOf(fishCatch.note) }
    var message by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.edit_catch),
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FishingTrackerCard {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.catch_form),
                        style = MaterialTheme.typography.titleLarge
                    )

                    OutlinedTextField(
                        value = fishType,
                        onValueChange = { fishType = it },
                        label = { Text(stringResource(R.string.fish_type)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text(stringResource(R.string.weight_kg)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = length,
                            onValueChange = { length = it },
                            label = { Text(stringResource(R.string.length_cm)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text(stringResource(R.string.location)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = bait,
                        onValueChange = { bait = it },
                        label = { Text(stringResource(R.string.bait)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = dateText,
                        onValueChange = { dateText = it },
                        label = { Text(stringResource(R.string.date)) },
                        placeholder = { Text(stringResource(R.string.date_hint)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = released,
                            onCheckedChange = { released = it }
                        )

                        Text(text = stringResource(R.string.released))
                    }

                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text(stringResource(R.string.note)) },
                        placeholder = { Text(stringResource(R.string.note_hint)) },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Button(
                onClick = {
                    val parsedWeight = weight.replace(",", ".").toFloatOrNull()
                    val parsedLength = length.replace(",", ".").toFloatOrNull()
                    val parsedDate = dateFormat.parseDateSafely(dateText)

                    when {
                        fishType.isBlank() || weight.isBlank() || length.isBlank() || location.isBlank() -> {
                            message = fillRequiredFieldsMessage
                        }

                        parsedWeight == null || parsedWeight <= 0f -> {
                            message = weightPositiveErrorMessage
                        }

                        parsedLength == null || parsedLength <= 0f -> {
                            message = lengthPositiveErrorMessage
                        }

                        parsedDate == null -> {
                            message = invalidDateMessage
                        }

                        else -> {
                            viewModel.updateCatch(
                                id = catchId,
                                fishType = fishType.trim(),
                                weight = parsedWeight,
                                length = parsedLength,
                                location = location.trim(),
                                bait = bait.trim(),
                                date = parsedDate.time,
                                released = released,
                                note = note.trim()
                            )

                            onSaveClick()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_changes))
            }

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.back))
            }

            if (message.isNotBlank()) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

private fun SimpleDateFormat.parseDateSafely(value: String): Date? {
    return try {
        isLenient = false
        parse(value)
    } catch (_: Exception) {
        null
    }
}