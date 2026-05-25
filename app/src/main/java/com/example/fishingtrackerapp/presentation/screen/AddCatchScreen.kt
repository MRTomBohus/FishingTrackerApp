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
fun AddCatchScreen(
    viewModel: CatchViewModel,
    onBackClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val todayText = dateFormat.format(Date())

    var fishType by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var length by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var bait by rememberSaveable { mutableStateOf("") }
    var dateText by rememberSaveable { mutableStateOf(todayText) }
    var released by rememberSaveable { mutableStateOf(false) }
    var note by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

    val fillRequiredFieldsMessage = stringResource(R.string.fill_required_fields)
    val weightPositiveErrorMessage = stringResource(R.string.weight_positive_error)
    val lengthPositiveErrorMessage = stringResource(R.string.length_positive_error)
    val invalidDateMessage = stringResource(R.string.invalid_date)
    val catchSavedMessage = stringResource(R.string.catch_saved)

    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.add_catch),
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
                            viewModel.addCatch(
                                fishType = fishType.trim(),
                                weight = parsedWeight,
                                length = parsedLength,
                                location = location.trim(),
                                bait = bait.trim(),
                                date = parsedDate.time,
                                released = released,
                                note = note.trim()
                            )

                            fishType = ""
                            weight = ""
                            length = ""
                            location = ""
                            bait = ""
                            dateText = todayText
                            released = false
                            note = ""

                            message = catchSavedMessage
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_catch))
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
                    color = if (message == catchSavedMessage) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
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