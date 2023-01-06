package com.example.composedemo.features_note.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composedemo.App
import com.example.composedemo.R
import com.example.composedemo.features_note.presentation.utils.Routes
import com.example.composedemo.features_note.presentation.utils.graphs.RootNavigationGraph
import com.example.composedemo.ui.theme.ComposeDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    private var prefKeys by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mApplication: App = applicationContext as App
        CoroutineScope(IO).launch {
            if (!mApplication.prefsUtils.authToken.equals(null))
                mApplication.prefsUtils.authToken.collect { str ->
                    str?.let {
                        prefKeys = str
                        Log.e("prefKey ", "$str")
                    }
                }
        }
        setContent {
            ComposeDemoTheme {
                RootNavigationGraph(navController = rememberNavController(), prefKeys)
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(Routes.ROUTE_SIGN_IN)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.splash_icon),
            contentDescription = "splash screen",
            modifier = Modifier.size(100.dp)
        )
    }
}

/*NavHost(
                    navController = navController,
                    startDestination = Routes.ROUTE_SPLASH_SCREEN
                ) {
                    composable(Routes.ROUTE_SPLASH_SCREEN) {
                        SplashScreen(navController = navController)
                    }
                    composable(Routes.ROUTE_SIGN_IN) {
                        SignInScreen(onNavigate = { event ->
                            navController.navigate(event.route) {
                                popUpTo(Routes.ROUTE_SPLASH_SCREEN) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                    composable(route = Routes.ROUTE_SIGN_UP) {
                        SignupScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            onPopBackStack = {
                                navController.popBackStack()
                            })
                    }
                    composable(route = Routes.ROUTE_HOME_SCREEN) {
                        HomeScreen(onNavigate = { event ->
                            navController.navigate(event.route)
                        })
                    }
                    composable(
                        route = Routes.ROUTE_ADD_EDIT + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }

                }*/