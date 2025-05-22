package com.example.moviediscovery.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.moviediscovery.R

sealed class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        titleResId = R.string.bottom_nav_home,
        icon = Icons.Default.Home
    )

    object Settings : BottomNavItem(
        route = Screen.Settings.route,
        titleResId = R.string.bottom_nav_settings,
        icon = Icons.Default.Settings
    )
}