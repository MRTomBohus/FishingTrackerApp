package com.example.fishingtrackerapp.presentation.navigation

/**
 * Represents all navigation destinations used in the application.
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AddCatch : Screen("add_catch")
    data object CatchList : Screen("catch_list")
    data object CatchDetail : Screen("catch_detail/{catchId}")
    data object EditCatch : Screen("edit_catch/{catchId}")
    data object Statistics : Screen("statistics")
    data object MapSpots : Screen("map_spots")

    data object Settings : Screen("settings")

    companion object {
        fun catchDetailRoute(catchId: Int): String {
            return "catch_detail/$catchId"
        }

        fun editCatchRoute(catchId: Int): String {
            return "edit_catch/$catchId"
        }
    }
}