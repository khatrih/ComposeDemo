package com.example.composedemo.demoactivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import com.example.composedemo.R
import com.example.composedemo.ui.theme.ComposeDemoTheme
import kotlinx.coroutines.launch

class DesignActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        Text(text = "Drawer title",
                            modifier = Modifier.padding(16.dp))
                        Divider()
                    },
                    drawerGesturesEnabled = true,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    scaffoldState.drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Text(text = "Drawer")
                        }
                    },
                    isFloatingActionButtonDocked = true,
                    bottomBar = {
                        BottomAppBar(
                            cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(50))
                        ) {
                            Icon(modifier = Modifier
                                .padding(start = 25.dp)
                                .padding(16.dp),
                                painter = painterResource(id = R.drawable.ic_home),
                                contentDescription = null,
                                tint = Color.Black.copy(0.4f)
                            )
                            Icon(modifier = Modifier
                                .padding(start = 95.dp)
                                .padding(16.dp),
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Icon(modifier = Modifier
                                .padding(start = 95.dp)
                                .padding(16.dp),
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                ) {
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                        color = MaterialTheme.colors.background) {
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            CallingComposable()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CallingComposable(modifier: Modifier = Modifier) {
    MyBasicColumn(modifier.padding(8.dp)) {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}

@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Button(onClick = {
        Toast.makeText(context, "rectangle like", Toast.LENGTH_SHORT).show()
    }, contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)) {
        Icon(Icons.Filled.Favorite,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize))
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "Like")
    }

    Spacer(modifier = Modifier.width(8.dp))

    ExtendedFloatingActionButton(icon = {
        Icon(Icons.Filled.Favorite, contentDescription = "")
    },
        text = { Text(text = "Like") },
        onClick = { Toast.makeText(context, "rounded like", Toast.LENGTH_SHORT).show() })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Row {
            //HomeScreen()
            CallingComposable()
        }
    }
}