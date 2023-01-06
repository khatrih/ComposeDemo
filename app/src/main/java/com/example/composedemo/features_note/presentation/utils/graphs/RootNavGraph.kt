package com.example.composedemo.features_note.presentation.utils.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavigationGraph(navController: NavHostController, prefKeys: Int) {

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if (prefKeys != 0) Graph.HOME else Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeNav()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val AUTHENTICATION = "auth_graph"
    const val DETAILS = "details_graph"
}