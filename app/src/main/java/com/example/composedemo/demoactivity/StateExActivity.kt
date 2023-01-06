package com.example.composedemo.demoactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composedemo.R
import com.example.composedemo.demoactivity.main.BottomNav
import com.example.composedemo.demoactivity.main.BottomNavNoAnimation
import com.example.composedemo.demoactivity.main.Screen

class StateExActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MainContent()
            //MainContents()
            //val items = listOf(Screen.Home, Screen.Create, Screen.Settings, Screen.Person)
            //Scaffold(bottomBar = { BottomNavNoAnimation(screens = items) }) { }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNav(navController = navController) }
    ) {
        NavHost(modifier = Modifier.padding(it),
            navController = navController, startDestination = "home") {
            composable("home") { AppScreen(text = "Home Screen") }
            composable("fav") { AppScreen(text = "Favourite Screen") }
            composable("feed") { AppScreen(text = "Feed Screen") }
            composable("profile") { AppScreen(text = "Profile Screen") }
        }
    }
}

@Composable
fun AppScreen(text: String) = Surface(modifier = Modifier.fillMaxSize()) {
    Text(text = text, fontSize = 32.sp)
}

data class Users(val id: Int)

@Composable
fun MainContent() {
    val context = LocalContext.current
    val user = Users(1)
    val users = remember { mutableStateListOf(user) }
    Box(modifier = Modifier.fillMaxSize()) {
        UsersList(users = users)
        Button(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                users.add(Users(1))
            }) {
            Text(text = "Submit")
        }
        Button(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomStart),
            onClick = {
                if (users.size > 1) {
                    users.removeAt(1)
                    Toast.makeText(context, "Item deleted ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "default 1 element we needed", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
            Text(text = "Remove")
        }
    }
}

@Composable
fun UsersList(users: List<Users>) {
    LazyColumn { items(users) { UsersCard() } }
}

@Composable
fun UsersCard() {
    Card(elevation = 4.dp, modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .wrapContentHeight()) {
        Row(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .border(width = 1.dp, color = Color.White)
            .padding(12.dp)) {
            Image(painter = painterResource(id = R.drawable.portrait_profile),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .padding(4.dp)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape))
            Column {
                Text(text = "Jetpack Compose ")
                Button(onClick = {}) {
                    Text(text = "View Profile")
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    Surface(Modifier.fillMaxSize()) {
        MainScreen()
        //MainContent()
    }
}
