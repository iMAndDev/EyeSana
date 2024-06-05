package ua.nure.maksymburym.eyesana.ui.dashboard

import android.content.Intent
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.nure.maksymburym.eyesana.R
import ua.nure.maksymburym.eyesana.ui.base.BaseComposeActivity
import ua.nure.maksymburym.eyesana.ui.dashboard.navDrawer.BottomNavItem
import ua.nure.maksymburym.eyesana.ui.base.kit.BottomNavBar
import ua.nure.maksymburym.eyesana.ui.base.kit.TopBar
import ua.nure.maksymburym.eyesana.ui.diary.DiaryScreen
import ua.nure.maksymburym.eyesana.ui.profile.ProfileScreen
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme
import ua.nure.maksymburym.eyesana.utils.getStringRes

@AndroidEntryPoint
class MainActivity : BaseComposeActivity() {

    @Composable
    override fun SetContent() {
        Dashboard { destination ->
            startActivity(Intent(this, destination))
        }
    }
}

@Composable
inline fun Dashboard(crossinline startActivityCallback: (Class<out ComponentActivity>) -> Unit = {}) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(
                title = getStringRes(id = R.string.app_name),
                backgroundColor = getColorScheme().secondaryContainer,
                contentColor = getColorScheme().primary,
                isStartNavIconVisible = false,
            )
        },
        bottomBar = {
            val bottomNavItems = listOf(
                BottomNavItem.Exercise,
                BottomNavItem.Diary,
                BottomNavItem.Profile
            )
            BottomNavBar(bottomNavItems, navController)
        },
    ) { innerPadding ->
        NavigationHost(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController,
            startActivityCallback = startActivityCallback
        )
    }
}

@Composable
inline fun NavigationHost(
    modifier: Modifier,
    navController: NavHostController,
    crossinline startActivityCallback: (Class<out ComponentActivity>) -> Unit
) {
    NavHost(navController, startDestination = BottomNavItem.Exercise.route) {
        composable(BottomNavItem.Exercise.route) {
            DashboardScreen(modifier, startActivityCallback)
        }
        composable(BottomNavItem.Diary.route) {
            DiaryScreen(modifier)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(modifier)
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardPreview() {
    Dashboard()
}
