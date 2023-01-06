
package com.example.composedemo.features_note.presentation.bottombar
/*
import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.composedemo.demoactivity.main.BottomNav
import com.example.composedemo.features_note.presentation.main.signin.SignInScreen
import com.example.composedemo.features_note.presentation.main.signup.SignupScreen
import com.example.composedemo.features_note.presentation.utils.Routes

sealed class StartupNavigationScreens(val route: String) {
    object Login : StartupNavigationScreens("login")
    object Register : StartupNavigationScreens("register")
    object Main : StartupNavigationScreens("main")
}

sealed class MainNavigationScreens(val route: String) {
    object Home : MainNavigationScreens("home")
    object Secondary : MainNavigationScreens("second")
}

@Composable
fun Main() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = StartupNavigationScreens.Login.route
    ) {
        composable(StartupNavigationScreens.Login.route) {
            SignInScreen(onNavigate = { event ->
                navController.navigate(event.route) {
                    popUpTo(Routes.ROUTE_SPLASH_SCREEN) {
                        inclusive = true
                    }
                }
            })
        }
        composable(StartupNavigationScreens.Register.route) {
            SignupScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                })
        }
        mainGraph(navController) //notice no composable but will go to the mainGraph
    }
}


fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = MainNavigationScreens.Home.route,
        route = StartupNavigationScreens.Main.route
    ) {

    }
}

@Composable
fun Home(navController: NavController) {
    Text(text = "home", Modifier.clickable {
        navController.navigate("second")
    })
}

@Composable
fun Secondary(navController: NavController) {
    Text(text = "Secondary", Modifier.clickable {
        navController.navigate("home")
    })
}

@Composable
fun HomeScreens(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNav(navController = navController) }
    ) {
        val p = it

        NavHost(
            navController = navController,
            startDestination = MainNavigationScreens.Home.route
        ) {
            composable(MainNavigationScreens.Home.route) {
                Home(navController)
            }
            composable(MainNavigationScreens.Secondary.route) {
                Secondary(navController)
            }
        }
    }
}




*/
