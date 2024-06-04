package ua.nure.maksymburym.eyesana.ui.base.kit

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.nure.maksymburym.eyesana.ui.dashboard.navDrawer.BottomNavItem
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
fun BottomNavBar(
    bottomNavItems: List<BottomNavItem>,
    navController: NavController,
) {
    BottomNavigation(
        backgroundColor = getColorScheme().tertiaryContainer,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: NavDestination? = navBackStackEntry?.destination

        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    /*
                     * Pop up to the start destination of the graph to
                     * avoid building up a large stack of destinations
                     * on the back stack as users select items
                     */
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = getColorScheme().onTertiaryContainer,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = getColorScheme().onTertiaryContainer,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp,
                    )
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun BottomNavBarPreview() {
    val bottomNavItems = listOf(
        BottomNavItem.Exercise,
        BottomNavItem.Diary,
        BottomNavItem.Profile
    )
    val navController = rememberNavController()
    BottomNavBar(bottomNavItems, navController)
}