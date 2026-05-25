package com.example.fishingtrackerapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SoftAqua,
    onPrimary = DeepRiverBlue,
    primaryContainer = DeepRiverBlue,
    onPrimaryContainer = SoftAqua,
    secondary = SoftGreen,
    onSecondary = DeepForestGreen,
    secondaryContainer = DeepForestGreen,
    onSecondaryContainer = SoftGreen,
    background = Color(0xFF101513),
    onBackground = Color(0xFFEAF2EE),
    surface = Color(0xFF151C19),
    onSurface = Color(0xFFEAF2EE),
    surfaceVariant = Color(0xFF20302B),
    outlineVariant = Color(0xFF42524C)
)

private val LightColorScheme = lightColorScheme(
    primary = RiverBlue,
    onPrimary = Color.White,
    primaryContainer = SoftAqua,
    onPrimaryContainer = DeepRiverBlue,
    secondary = ForestGreen,
    onSecondary = Color.White,
    secondaryContainer = SoftGreen,
    onSecondaryContainer = DeepForestGreen,
    background = WarmSand,
    onBackground = DarkText,
    surface = Color(0xFFFFFBF2),
    onSurface = DarkText,
    surfaceVariant = Color(0xFFEAF2EE),
    outlineVariant = Color(0xFFC8D8D3)
)

@Composable
fun FishingTrackerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}