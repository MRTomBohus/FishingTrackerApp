package com.example.fishingtrackerapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fishingtrackerapp.presentation.screen.AddCatchScreen
import com.example.fishingtrackerapp.presentation.screen.CatchListScreen
import com.example.fishingtrackerapp.presentation.screen.HomeScreen
import com.example.fishingtrackerapp.presentation.screen.MapSpotsScreen
import com.example.fishingtrackerapp.presentation.screen.StatisticsScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.fishingtrackerapp.presentation.screen.CatchDetailScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fishingtrackerapp.presentation.viewmodel.CatchViewModel
import com.example.fishingtrackerapp.presentation.screen.EditCatchScreen
import androidx.compose.ui.platform.LocalContext
import com.example.fishingtrackerapp.FishingTrackerApplication
import com.example.fishingtrackerapp.presentation.viewmodel.CatchViewModelFactory
import com.example.fishingtrackerapp.presentation.viewmodel.FishingSpotViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fishingtrackerapp.presentation.components.FishingTrackerBottomBar
import com.example.fishingtrackerapp.presentation.screen.SettingsScreen
import com.example.fishingtrackerapp.presentation.viewmodel.WeatherViewModel

/**
 * Defines the main navigation graph and shared screen ViewModels.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val application = context.applicationContext as FishingTrackerApplication

    val catchViewModel: CatchViewModel = viewModel(
        factory = CatchViewModelFactory(application.catchRepository)
    )

    val fishingSpotViewModel: FishingSpotViewModel = viewModel()
    val weatherViewModel: WeatherViewModel = viewModel()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.AddCatch.route,
        Screen.CatchList.route,
        Screen.Statistics.route,
        Screen.MapSpots.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                FishingTrackerBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onAddCatchClick = {
                        navController.navigate(Screen.AddCatch.route)
                    },
                    onMyCatchesClick = {
                        navController.navigate(Screen.CatchList.route)
                    },
                    onStatisticsClick = {
                        navController.navigate(Screen.Statistics.route)
                    },
                    onMapSpotsClick = {
                        navController.navigate(Screen.MapSpots.route)
                    },
                    onSettingsClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.AddCatch.route) {
                AddCatchScreen(
                    viewModel = catchViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.CatchList.route) {
                CatchListScreen(
                    viewModel = catchViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCatchClick = { catchId ->
                        navController.navigate(Screen.catchDetailRoute(catchId))
                    }
                )
            }

            composable(
                route = Screen.CatchDetail.route,
                arguments = listOf(
                    navArgument("catchId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val catchId = backStackEntry.arguments?.getInt("catchId") ?: 0

                CatchDetailScreen(
                    catchId = catchId,
                    viewModel = catchViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate(Screen.editCatchRoute(catchId))
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.EditCatch.route,
                arguments = listOf(
                    navArgument("catchId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val catchId = backStackEntry.arguments?.getInt("catchId") ?: 0

                EditCatchScreen(
                    catchId = catchId,
                    viewModel = catchViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Statistics.route) {
                StatisticsScreen(
                    viewModel = catchViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.MapSpots.route) {
                MapSpotsScreen(
                    viewModel = fishingSpotViewModel,
                    weatherViewModel = weatherViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}