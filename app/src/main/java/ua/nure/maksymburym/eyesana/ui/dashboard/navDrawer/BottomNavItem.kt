package ua.nure.maksymburym.eyesana.ui.dashboard.navDrawer

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.vector.ImageVector
import ua.nure.maksymburym.eyesana.R

sealed class BottomNavItem(
    @StringRes var titleRes: Int,
    var icon: ImageVector,
    var route: String
) {

    data object Exercise : BottomNavItem(
        titleRes = R.string.exercise,
        icon = Icons.Default.Home,
        route = "exercise"
    )
    data object Diary : BottomNavItem(
        titleRes = R.string.acuity,
        icon = Icons.Filled.Visibility,
        route = "diary"
    )
    data object Profile : BottomNavItem(
        titleRes = R.string.profile,
        icon = Icons.Default.AccountCircle,
        route = "profile"
    )
}