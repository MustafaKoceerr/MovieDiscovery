package com.example.moviediscovery.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviediscovery.presentation.detail.DetailScreen
import com.example.moviediscovery.presentation.home.HomeScreen
import com.example.moviediscovery.presentation.search.SearchScreen

@Composable
fun MovieNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId: Int ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}








