package ua.nure.maksymburym.eyesana.ui.dashboard.navDrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {

    data object Exercise : BottomNavItem(
        title = "Exercise",
        icon = Icons.Default.Home,
        route = "exercise"
    )
    data object Diary : BottomNavItem(
        title = "Diary",
        icon = Icons.AutoMirrored.Filled.List,
        route = "diary"
    )
    data object Profile : BottomNavItem(
        title = "Profile",
        icon = Icons.Default.AccountCircle,
        route = "profile"
    )
}