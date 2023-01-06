package com.example.composedemo.demoactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.R
import com.example.composedemo.ui.theme.ComposeDemoTheme

class NextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                    color = MaterialTheme.colors.background) {

                    //ArtistCard()
                    //ListComposable(myList = listOf("text1", "text2", "text3", "text4"))

                    //var name by remember { mutableStateOf("") }
                    //NamePicker(header = name,
                    //    names = listOf("text1", "text2", "text3", "text4"),
                    //    onNameClicked = { name = it })

                    //HelloScreen()
                    //CityScreen()
                    UserList()
                }
            }
        }
    }
}

data class User(val Id: Int)

val user = listOf(
    User(1),
    User(2),
    User(3),
    User(4),
    User(5),
    User(6),
    User(7),
    User(8)
)

@Composable
fun UserList() {
    /*Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for (i in 1..10)
            UserCard()
    }*/

    LazyColumn() {
        items(user) {
            UserCard()
        }
    }
}

@Composable
fun UserCard() {
    val context = LocalContext.current
    Card(elevation = 8.dp, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
        backgroundColor = Color.LightGray
    ) {
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
                Text(text = "Jetpack Compose")
                Button(onClick = {
                    val intent = Intent(context, StateExActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text(text = "View Profile")
                }
            }
        }
    }
}

data class City(val name: String, val country: String)

@Composable
fun CityScreen() {
    val citySaver = run {
        val nameKey = "Name"
        val countryKey = "Country"
        mapSaver(save = { mapOf(nameKey to it.name, countryKey to it.country) },
            restore = { City(it[nameKey] as String, it[countryKey] as String) })
    }
    rememberSaveable(stateSaver = citySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}

@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        //var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5)
        }
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
    }
}

//Composable functions can run in parallel
@Composable
fun ListComposable(myList: List<String>) {
    //var items = 0
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) {
                Text("Item : $item")
                //items++
            }
        }
        Text(text = "Count: ${myList.size}"/*$items*/)
    }
}

//Recomposition skips as much as possible
@Composable
fun NamePicker(header: String, names: List<String>, onNameClicked: (String) -> Unit) {
    Column {
        //This will recompose when [header] changes, but not when [names] changes
        Text(header, style = MaterialTheme.typography.h5)
        Divider()

        //LazyColumn is the compose version of a RecyclerView.
        //The ;ambda passed to items() is similar to a RecyclerView.ViewHolder.
        LazyColumn {
            items(names) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, onNameClicked)
            }
        }
    }
}

@Composable
fun NamePickerItem(name: String, onNameClicked: (String) -> Unit) {
    Text(name, Modifier.clickable(onClick = { onNameClicked(name) }))
}

@Composable
fun ArtistCard() {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = "Hello1")
            Text(text = "Hello2")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ComposeDemoTheme {
        Surface(Modifier.fillMaxSize()) {
            //ArtistCard()
            //ListComposable(myList = listOf("text1", "text2", "text3", "text4"))
            //CityScreen()
            //UserCard()
            UserList()
        }
    }
}
