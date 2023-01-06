package com.example.composedemo.features_note.presentation.main.signup

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.features_note.domain.model.User
import com.example.composedemo.features_note.domain.use_case.UsersUseCase
import com.example.composedemo.features_note.presentation.utils.AuthEvent
import com.example.composedemo.features_note.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SIgnUpViewModel @Inject constructor(private val useCase: UsersUseCase) :
    ViewModel() {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var userName by mutableStateOf("")
        private set
    var userEmail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChange -> {
                userEmail = event.email
            }
            is AuthEvent.OnNameChange -> {
                userName = event.name
            }
            is AuthEvent.OnPasswordChange -> {
                userPassword = event.password
            }
            is AuthEvent.OnSubmitClick -> {
                viewModelScope.launch {
                    if (userName.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar("The username can't be empty."))
                        return@launch
                    }
                    if (userEmail.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar("The useremail can't be empty."))
                        return@launch
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                        sendUIEvent(UIEvent.ShowSnackbar("The user-email is not valid."))
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
                    val user = User(
                        userName = userName,
                        userEmail = userEmail,
                        userPassword = userPassword
                    )
                    useCase.invoke(user = user)
                    sendUIEvent(UIEvent.PopBackStack)
                }
            }
            is AuthEvent.OnSignInClick -> {
                sendUIEvent(UIEvent.PopBackStack)
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