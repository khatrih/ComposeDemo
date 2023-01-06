package com.example.composedemo.features_note.presentation.main.add_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composedemo.features_note.presentation.utils.UIEvent
import com.example.composedemo.ui.theme.BlackText
import com.example.composedemo.ui.theme.LightBlackText
import com.example.composedemo.ui.theme.LightSaffron

@Composable
fun AddEditScreen(
    onPopBackStack: () -> Unit, viewModel: AddEditViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> onPopBackStack()

                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message, actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Subscription",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace
                    )
                },
                backgroundColor = LightSaffron,
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(AddEditEvent.OnPopBackStack)
                    }) {
                        Icon(
                            Icons.Filled.ArrowBackIos,
                            contentDescription = "backArrow",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditEvent.OnTitleChange(string))
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ModeEdit,
                        contentDescription = "package name"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = BlackText,
                    focusedBorderColor = BlackText,
                    focusedLabelColor = BlackText,
                    unfocusedLabelColor = LightBlackText,
                    placeholderColor = LightBlackText,
                    trailingIconColor = LightBlackText,
                    leadingIconColor = LightBlackText,
                    cursorColor = LightBlackText,
                    textColor = BlackText
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                label = { Text(text = "subscription name") },
                placeholder = { Text(text = "enter subscription name") },
                keyboardOptions = KeyboardOptions.Default
                    .copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
            )

            OutlinedTextField(
                value = viewModel.price,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditEvent.OnPriceChange(string))
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CurrencyRupee,
                        contentDescription = "currency"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlackText,
                    unfocusedBorderColor = BlackText,
                    focusedLabelColor = BlackText,
                    unfocusedLabelColor = LightBlackText,
                    placeholderColor = LightBlackText,
                    trailingIconColor = LightBlackText,
                    leadingIconColor = LightBlackText,
                    cursorColor = LightBlackText,
                    textColor = BlackText
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                label = { Text(text = "price") },
                placeholder = { Text(text = "enter price") },
                keyboardOptions = KeyboardOptions.Default
                    .copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
            )

            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditEvent.OnDescriptionChange(string))
                },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Filled.Description, contentDescription = "Description")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlackText,
                    unfocusedBorderColor = BlackText,
                    focusedLabelColor = BlackText,
                    unfocusedLabelColor = LightBlackText,
                    placeholderColor = LightBlackText,
                    trailingIconColor = LightBlackText,
                    leadingIconColor = LightBlackText,
                    cursorColor = LightBlackText,
                    textColor = BlackText
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                label = { Text(text = "Description") },
                placeholder = { Text(text = "enter description") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            DropDownOption(viewModel)

            Button(onClick = {
                viewModel.onEvent(AddEditEvent.OnSaveTodoClick)
            },
                colors = ButtonDefaults.buttonColors(LightSaffron),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally)) {
                Text(
                    text = "submit",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun DropDownOption(viewModel: AddEditViewModel) {

    val mPayOptions = listOf("cash", "debit card", "credit card")
    var mExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon =
        if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier
        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
    ) {

        OutlinedTextField(
            readOnly = true,
            value = viewModel.payOption,
            onValueChange = { string ->
                viewModel.onEvent(AddEditEvent.OnPayOptionChange(string))
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlackText,
                unfocusedBorderColor = BlackText,
                focusedLabelColor = BlackText,
                unfocusedLabelColor = LightBlackText,
                placeholderColor = LightBlackText,
                trailingIconColor = LightBlackText,
                cursorColor = LightBlackText,
                textColor = BlackText
            ),
            placeholder = { Text("select options") },
            label = { Text("Label") },
            trailingIcon = {
                Icon(icon, "drop-down",
                    Modifier.clickable {
                        mExpanded = !mExpanded
                    })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            mPayOptions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.payOption = label
                        mExpanded = false
                    }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreviewScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        //AddEditScreen()
    }
}