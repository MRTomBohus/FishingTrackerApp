package com.example.fishingtrackerapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fishingtrackerapp.R

/**
 * Represents one item shown in the bottom navigation bar.
 */
data class BottomNavigationItem(
    val route: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
)

/**
 * Bottom navigation items used by the main app navigation.
 */
val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = Screen.AddCatch.route,
        labelResId = R.string.nav_add,
        icon = Icons.Filled.AddCircle
    ),
    BottomNavigationItem(
        route = Screen.CatchList.route,
        labelResId = R.string.nav_catches,
        icon = Icons.Filled.List
    ),
    BottomNavigationItem(
        route = Screen.Statistics.route,
        labelResId = R.string.nav_stats,
        icon = Icons.Filled.BarChart
    ),
    BottomNavigationItem(
        route = Screen.MapSpots.route,
        labelResId = R.string.nav_spots,
        icon = Icons.Filled.Place
    )
)