package com.example.composedemo.features_note.presentation.main.signin

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.features_note.domain.use_case.UsersUseCase
import com.example.composedemo.features_note.presentation.utils.AuthEvent
import com.example.composedemo.features_note.presentation.utils.PrefsUtils
import com.example.composedemo.features_note.presentation.utils.UIEvent
import com.example.composedemo.features_note.presentation.utils.graphs.AuthScreen
import com.example.composedemo.features_note.presentation.utils.graphs.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCase: UsersUseCase, private val prefsUtils: PrefsUtils
) : ViewModel() {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var userEmail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChange -> {
                userEmail = event.email
            }
            is AuthEvent.OnPasswordChange -> {
                userPassword = event.password
            }
            is AuthEvent.OnSignUpClick -> {
                sendUIEvent(UIEvent.Navigate(AuthScreen.SignUp.route))
            }
            is AuthEvent.OnSubmitClick -> {
                viewModelScope.launch {
                    if (userEmail.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar("The useremail can't be empty."))
                        return@launch
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                        sendUIEvent(UIEvent.ShowSnackbar("The useremail is not valid."))
                        return@launch
                    }
                    if (userPassword.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar("The password can't be empty."))
                        return@launch
                    }
                    if (userPassword.length < 8 || userPassword.length > 16) {
                        sendUIEvent(UIEvent.ShowSnackbar("password should be 8 to 16 characters"))
                        return@launch
                    }
                    useCase.verifyEmail(userEmail).collect { userModel ->
                        if (userModel != null && userModel.userEmail == userEmail) {
                            useCase.signUpListener(userEmail, userPassword).collect {
                                if (it != null && it.userPassword == userPassword) {
                                    sendUIEvent(UIEvent.Navigate(Graph.HOME))
                                    prefsUtils.authKey(userModel.userEmail)
                                    prefsUtils.authToken(userModel.uId!!)
                                } else {
                                    sendUIEvent(UIEvent.ShowSnackbar("password does not match. try again!"))
                                }
                            }
                        } else {
                            sendUIEvent(UIEvent.ShowSnackbar("user does not exist"))
                        }
                    }
                    //sendUIEvent(UIEvent.PopBackStack)
                }
            }
            else -> Unit
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}