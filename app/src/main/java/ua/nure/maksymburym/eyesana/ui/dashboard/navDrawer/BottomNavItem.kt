package ua.nure.maksymburym.eyesana.ui.dashboard.navDrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ua.nure.maksymburym.eyesana.R

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {

    data object Exercise : BottomNavItem(
        title = "Exercise",
//        icon = android.R.drawable.ic_dialog_dialer,
        icon = Icons.Default.Home,
        route = "exercise"
    )
    data object Diary : BottomNavItem(
        title = "Diary",
//        icon = android.R.drawable.ic_menu_agenda,
        icon = Icons.Default.List,
        route = "diary"
    )
    data object Profile : BottomNavItem(
        title = "Profile",
//        icon = R.drawable.ic_profile,
        icon = Icons.Default.AccountCircle,
        route = "profile"
    )
}