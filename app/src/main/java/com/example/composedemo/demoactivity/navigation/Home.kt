package com.example.composedemo.demoactivity.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composedemo.R

@Composable
fun Home(navController: NavHostController? = null) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = "",
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(5.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    ""
                )
            }
        )
        TitleText(text = "My Tasks")
        LazyColumn {
            var i = 0
            items((0..4).toList()) {
                TaskCard("Item ${++i}", navController)
            }
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(text = text, style = MaterialTheme.typography.h5)
}

@Composable
fun TaskCard(task: String, navController: NavHostController? = null) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
            .clickable {
                navController?.navigate("task")
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.portrait_profile),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 32.dp)
                    .size(32.dp)

            )
            NormalText(text = task)
        }
    }
}

@Composable
fun NormalText(text: String) {
    Text(text = text, style = MaterialTheme.typography.h5)
}


@Preview
@Composable
fun HomePreview() {
    Home()
}