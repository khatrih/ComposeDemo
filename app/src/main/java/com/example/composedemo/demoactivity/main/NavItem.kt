package com.example.composedemo.demoactivity.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composedemo.R

sealed class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val navRoute: String,
) {
    object Home : NavItem(R.string.home, R.drawable.ic_home, "home")
    object Fav : NavItem(R.string.fav, R.drawable.ic_favorite, "fav")
    object Feed : NavItem(R.string.feed, R.drawable.ic_feed, "feed")
    object Profile : NavItem(R.string.profile, R.drawable.ic_profile, "profile")
}

sealed class Screen(
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector,
) {
    object Home : Screen("Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Create : Screen("Create", Icons.Filled.Create, Icons.Outlined.Create)
    object Settings : Screen("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    object Person : Screen("Person", Icons.Filled.Person, Icons.Outlined.Person)

}