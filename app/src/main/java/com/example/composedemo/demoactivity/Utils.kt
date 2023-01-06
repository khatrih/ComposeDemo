package com.example.composedemo.demoactivity

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable

@Composable
fun Context.GetAppBar() {
    TopAppBar(
        title = {
            Text(text = "Compose Demo")
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        }, actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Notifications,
                    contentDescription = "notifications")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Search, contentDescription = "search")
            }
            IconButton(onClick = {
                Toast.makeText(this@GetAppBar, "click", Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Filled.Create, contentDescription = "create")
            }
        }
    )
}