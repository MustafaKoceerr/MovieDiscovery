package com.example.moviediscovery.presentation.navigation


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }

    object Search : Screen("search")
    object Settings : Screen("settings")
}