package com.example.composedemo.features_note.presentation.main.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composedemo.R
import com.example.composedemo.features_note.presentation.utils.AuthEvent
import com.example.composedemo.features_note.presentation.utils.UIEvent
import com.example.composedemo.ui.theme.BlackText
import com.example.composedemo.ui.theme.LightBlackText
import com.example.composedemo.ui.theme.LightSaffron

@Composable
fun SignupScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: SIgnUpViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val userPasswordVisible = rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> {
                    Log.e("sign up", "backStack:")
                    onPopBackStack()
                }

                is UIEvent.Navigate -> {
                    Log.e("sign up", "navigate: ${event.route}")
                    onNavigate(event)
                }

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
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(LightSaffron)
                .padding(paddingValues)
        ) {

            Column {

                Text(
                    text = "Welcome",
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp),
                    color = Color.White
                )

                Text(
                    text = "Sign Up",
                    modifier = Modifier
                        .padding(start = 24.dp, top = 2.dp),
                    color = Color.White,
                    fontSize = 25.sp
                )

            }

            Column(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(Color.White)
                    .fillMaxSize()
            ) {

                OutlinedTextField(
                    value = viewModel.userName,
                    onValueChange = { viewModel.onEvent(AuthEvent.OnNameChange(it)) },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    leadingIcon = {
                        Icon(Icons.Filled.Person, contentDescription = "name")
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
                        .padding(start = 16.dp, top = 80.dp, end = 16.dp),
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "enter name") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = viewModel.userEmail,
                    onValueChange = { viewModel.onEvent(AuthEvent.OnEmailChange(it)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Filled.Email, contentDescription = "email")
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
                        .padding(start = 16.dp, top = 1.dp, end = 16.dp),
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "enter email") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })

                )

                OutlinedTextField(
                    value = viewModel.userPassword,
                    onValueChange = { viewModel.onEvent(AuthEvent.OnPasswordChange(it)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = "password")
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
                    visualTransformation = if (userPasswordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (userPasswordVisible.value) painterResource(id = R.drawable.ic_visibility)
                            else painterResource(id = R.drawable.ic_visibility_off)

                        val description =
                            if (userPasswordVisible.value) "Hide password" else "Show password"

                        IconButton(onClick = {
                            userPasswordVisible.value = !userPasswordVisible.value
                        }) {
                            Icon(image, description)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 1.dp, end = 16.dp, bottom = 16.dp),
                    label = { Text(text = "password") },
                    placeholder = { Text(text = "enter password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

                Button(
                    onClick = {
                        viewModel.onEvent(AuthEvent.OnSubmitClick)
                    },
                    colors = ButtonDefaults.buttonColors(LightSaffron),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {

                    Text(text = "Already have an account?", color = LightBlackText)

                    Spacer(modifier = Modifier.padding(end = 1.dp))

                    Text(text = "Sign In",
                        color = LightSaffron,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(AuthEvent.OnSignInClick)
                        }
                    )
                }
            }
        }
    }
}

