package ua.nure.maksymburym.eyesana.ui.dashboard

import android.content.res.Configuration
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
import ua.nure.maksymburym.eyesana.ui.base.BaseComposeActivity
import ua.nure.maksymburym.eyesana.ui.dashboard.navDrawer.BottomNavItem
import ua.nure.maksymburym.eyesana.ui.base.kit.BottomNavBar
import ua.nure.maksymburym.eyesana.ui.base.kit.TopBar
import ua.nure.maksymburym.eyesana.ui.diary.DiaryScreen
import ua.nure.maksymburym.eyesana.ui.profile.ProfileScreen
import ua.nure.maksymburym.eyesana.ui.resources.EyeSanaTheme
import ua.nure.maksymburym.eyesana.ui.resources.composableStrings

class MainActivity : BaseComposeActivity() {

    @Composable
    override fun SetContent() {
        Dashboard()
    }
}

@Composable
fun Dashboard(testingLambda: (() -> Unit)? = null) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(
                title = composableStrings().appName,
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
        floatingActionButton = {
//            FloatingActionButton(onClick = { presses++ }) {
//                Icon(Icons.Default.Add, contentDescription = "Add")
//            }
        }
    ) { innerPadding ->
        NavigationHost(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController,
            testingLambda
        )
//        Column(
//            modifier = Modifier
//                .background(color = getColorScheme().secondaryContainer)
//                .fillMaxSize()
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//        ) {
//            Text(
//                modifier = Modifier.padding(8.dp),
//                text =
//                """
//                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.
//
//                    It also contains some basic inner content, such as this text.
//
//                    You have pressed the floating action button $presses times.
//                """.trimIndent(),
//            )
//        }
    }
}

@Composable
fun NavigationHost(
    modifier: Modifier,
    navController: NavHostController,
    test: (() -> Unit)? = null
) {
    NavHost(navController, startDestination = BottomNavItem.Exercise.route) {
        composable(BottomNavItem.Exercise.route) {
            DashboardScreen(modifier, onButtonClick = {
                test?.invoke()
            })
        }
        composable(BottomNavItem.Diary.route) {
            DiaryScreen(modifier)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(modifier)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DashboardPreview() {
    EyeSanaTheme { Dashboard() }
}
