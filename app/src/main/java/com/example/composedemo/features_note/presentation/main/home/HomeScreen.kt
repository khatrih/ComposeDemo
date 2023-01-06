package com.example.composedemo.features_note.presentation.main.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composedemo.features_note.presentation.main.home.search.SearchBar
import com.example.composedemo.features_note.presentation.main.home.search.SearchDisplay
import com.example.composedemo.features_note.presentation.main.home.search.SearchState
import com.example.composedemo.features_note.presentation.main.home.search.rememberSearchState
import com.example.composedemo.features_note.presentation.utils.UIEvent
import com.example.composedemo.ui.theme.LightSaffron
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    onNavigate: (UIEvent.Navigate) -> Unit, viewModel: HomeViewModel = hiltViewModel(),
    state: SearchState = rememberSearchState()
) {

    val getPackages = viewModel.getPackages.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    //val showLoading = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(HomeListEvent.OnUndoDeleteClick)
                    }
                }
                is UIEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "menu",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = LightSaffron,
                actions = {
                    IconButton(onClick = {
                        //viewModel.onEvent(HomeListEvent.OnAddPackageClick)

                    }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "search",
                            tint = Color.White
                        )


                    }
                    IconButton(onClick = {
                        viewModel.onEvent(HomeListEvent.OnAddPackageClick)
                    }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add screen",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        //viewModel.onEvent(HomeListEvent.OnDeleteUserClick())
                    }) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "logout",
                            tint = Color.White,
                            modifier = Modifier.rotate(0f)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column {
            SearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                onBack = { state.query = TextFieldValue("") },
                searching = state.searching,
                focused = state.focused,
            )

            LaunchedEffect(state.query.text) {
                state.searching = true
                delay(100)
                state.searchResults = getPackages.value
                state.searching = false
            }

            when (state.searchDisplay) {
                SearchDisplay.InitialResults -> {

                }
                SearchDisplay.NoResults -> {

                }

                SearchDisplay.Suggestions -> {

                }

                SearchDisplay.Results -> {

                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                /*items(getPackages.value) { packages ->
                    repeat(1) {
                        LoadingShimmerEffect(showLoading.value, packages, viewModel)
                    }
                }*/
                items(getPackages.value) { packages ->
                    HomeItem(
                        packages = packages,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { viewModel.onEvent(HomeListEvent.OnPackageClick(packages)) }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}


//swipe delete
/*itemsIndexed(items = getPackages.value, key = { index, item2 ->
index.hashCode()
}) { index, item ->
val state = rememberDismissState(
    confirmStateChange = {
        if (it == DismissValue.DismissedToStart) {
            (getPackages.value as MutableList<Packages>).remove(item)
        }
        true
    }
)

SwipeToDismiss(
    state = state,
    background = {
        val color = when (state.dismissDirection) {
            DismissDirection.StartToEnd -> Color.Transparent
            DismissDirection.EndToStart -> Color.Red
            null -> Color.Transparent
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    },
    dismissContent = {
        HomeItem(
            packages = item,
            onEvent = { viewModel.onEvent(HomeListEvent.OnDeletePackageClick(item)) }
        )
    },
    directions = setOf(DismissDirection.EndToStart)
)
Divider()
}*/


