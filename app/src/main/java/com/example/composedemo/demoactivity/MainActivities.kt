package com.example.composedemo.demoactivity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.R
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivities : ComponentActivity() {
    //var onClick by mutableStateOf({})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //same as setContentView() in kotlin android
        setContent {
            //default theme
            ComposeDemoTheme {
                //for app bar
                Scaffold(
                    topBar = {
                        GetAppBar()
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            IconButton(onClick = { }) {
                                Icon(Icons.Filled.Add, contentDescription = "add")
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) {
                    Column(modifier = Modifier.padding(it)) {
                        ShowSwitch()
                        //NextActivityButton()
                        // A surface container using the 'background' color from the theme
                        //surface is one type of container here.
                        Surface(
                            modifier = Modifier.fillMaxHeight(),
                            color = MaterialTheme.colors.background
                        ) {
                            Conversation(SampleData.greetingSample)
                        }
                    }
                }
            }
        }
    }
}

data class GreetingStyles(val person: String, val variantOfGreeting: String)

@Composable
fun ShowSwitch() {
    val isChecked = remember { mutableStateOf(false) }
    Switch(
        checked = isChecked.value,
        onCheckedChange = {
            isChecked.value = it
        },
        modifier = Modifier.run {
            size(20.dp, 4.dp)
            padding(start = 4.dp, end = 4.dp)
        }
    )
    NextActivityButton(isChecked)
}

@Composable
fun NextActivityButton(isChecked: MutableState<Boolean>) {
    val context = LocalContext.current
    Box {
        if (isChecked.value) {
            Button(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .align(Alignment.TopEnd),
                onClick = {
                    context.startActivity(Intent(context, NextActivity::class.java))
                    Toast.makeText(context, "button has been clicked", Toast.LENGTH_SHORT).show()
                }) {
                Text(text = "next")
            }
        } else {
            Snackbar(modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 20.dp, end = 20.dp),
                action = {
                    IconButton(onClick = {
                        Toast.makeText(context, "please on switch", Toast.LENGTH_LONG).show()
                    }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "close"
                        )
                    }
                }) {
                Text(text = "please on switch")
            }
        }
    }
}

@Composable
fun Greeting(greeting: GreetingStyles) {
    //Text(text = "Hello $name!")
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.portrait_profile),
            contentDescription = "Default Android Icon",
            //Set image size to 40 dp
            modifier = Modifier
                .size(46.5.dp)
                //Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        //Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        //mutableState = store the value And in case i upgrade value trigger recompose for all element using this data
        //remember = store the value just in case recompose will be called
        //we keep track if the message is expanded or not in this variable
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.surface else MaterialTheme.colors.surface
        )

        //We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = greeting.person,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 18.sp,
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium, elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = greeting.variantOfGreeting,
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun Conversation(greetingStyle: List<GreetingStyles>) {
    LazyColumn {
        greetingStyle.map { item { Greeting(it) } }
    }
}

//multiple preview
@Preview(name = "ListOfGreetingStyle", showBackground = true)
@Composable
fun PreviewConversation() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        ShowSwitch()
        //NextActivityButton()
        Conversation(SampleData.greetingSample)
    }
}

//light mode preview
@Preview(name = "Light Mode")

//dark mode preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)

//normal mode preview
@Preview(showBackground = true, name = "Test Preview")
@Composable
fun DefaultPreview() {
    ComposeDemoTheme {
        //Greeting(GreetingStyles("Indian", "Namaste"))
        ShowSwitch()
        //NextActivityButton()
        Conversation(SampleData.greetingSample)
    }
}

object SampleData {
    // Sample conversation data
    val greetingSample = listOf(
        GreetingStyles(
            "India: Place your palms together and say “Namaste",
            "Most westerners get a handshake, but, if you're looking " +
                    "to seem less touristy, it’s all about Namaste — something yogis " +
                    "might remember. Place your palms together like a prayer, " +
                    "tilt your head forward, and say “Namaste,” which means, “adoration " +
                    "to you.”\n"
        ),
        GreetingStyles(
            "China: Bow or shake hands",
            "In formal settings, the Chinese bow, but, in recent years handshakes " +
                    "have become the norm. When introducing yourself, don't be surprised if you're " +
                    "expected to list your profession and the company for which you work. It's normal."
        ),
        GreetingStyles(
            "United States: Handshake, fist bump, hug, or wave",
            "There's the handshake, fist bump (Thanks, Obama), hug, bro-hug," +
                    " “the nod,” and the ever-endearing, half-excited wave. Take your pick.\n"
        ),
        GreetingStyles(
            "The United Kingdom: A handshake",
            "One thing that unhinges Brits more than disorganized queues and people " +
                    "who “stand on the left” is a kissy greeting. A handshake, preferably with " +
                    "little eye contact and some incoherent Hugh Grant-like mumbling, is ideal"
        ),
        GreetingStyles(
            "Thailand: Press your hands together and slightly bow",
            "There's only one correct way — or wai — to greet in Thailand," +
                    " and that's to press your hands together in a prayer like fashion and slightly " +
                    "bow to your acquaintance."
        ),
        GreetingStyles(
            "France: Kiss on the cheeks three or four times",
            "In France the cheek-to-cheek — or cheek-to-cheek-to-cheek — kiss is as regional as the country’s wines. " +
                    "In the same way you wouldn’t order a Merlot in Burgundy, you wouldn't want to kiss twice when, typically, they kiss four."
        ),
        GreetingStyles(
            "Japan: Bow",
            "The bow is the standard greeting in Japan. Depending on the formalities," +
                    " bows differ in duration, declination, and style. Among peers, " +
                    "the bow may be subtle, but don't dare bow that lightly to elders."
        ),
        GreetingStyles(
            "Germany: A firm handshake",
            "Most Germans despise lippy introductions. In fact, they hate " +
                    "it so much they've tried to abolish it. Stick to handshakes. " +
                    "It’s more efficient, as is the German way."
        )
    )
}