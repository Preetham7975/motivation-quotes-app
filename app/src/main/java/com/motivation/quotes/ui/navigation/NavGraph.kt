package com.motivation.quotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.motivation.quotes.ui.screens.FavoritesScreen
import com.motivation.quotes.ui.screens.HomeScreen
import com.motivation.quotes.viewmodel.QuoteViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
}

@Composable
fun QuoteNavGraph(
    navController: NavHostController,
    viewModel: QuoteViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
