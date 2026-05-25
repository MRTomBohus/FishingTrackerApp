package com.example.fishingtrackerapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fishingtrackerapp.R
import com.example.fishingtrackerapp.data.notification.NotificationHelper
import com.example.fishingtrackerapp.presentation.components.FishingTrackerCard
import com.example.fishingtrackerapp.presentation.components.FishingTrackerTopBar

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var message by rememberSaveable { mutableStateOf("") }
    val notificationSentMessage = stringResource(R.string.notification_sent)

    Scaffold(
        topBar = {
            FishingTrackerTopBar(
                title = stringResource(R.string.settings),
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FishingTrackerCard {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.test_notification),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = stringResource(R.string.notification_settings_description),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = {
                            NotificationHelper.showCatchReminder(context)
                            message = notificationSentMessage
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null
                        )

                        Text(
                            text = stringResource(R.string.test_notification),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    if (message.isNotBlank()) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}