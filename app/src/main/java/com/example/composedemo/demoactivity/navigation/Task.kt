package com.example.composedemo.demoactivity.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Task(navController: NavHostController? = null, item: String? = null) {
    val context = LocalContext.current
    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        TitleText(text = "Task Details")
        LazyColumn {
            var i = 0
            items((0..5).toList()) {
                TaskCard("Detail Item ${++i}", navController)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskPreview() {
    Task()
}