package com.example.composedemo.features_note.presentation.utils

sealed class AuthEvent {
    data class OnNameChange(val name: String) : AuthEvent()
    data class OnEmailChange(val email: String) : AuthEvent()
    data class OnPasswordChange(val password: String) : AuthEvent()
    object OnSignInClick : AuthEvent()
    object OnSignUpClick : AuthEvent()
    object OnSubmitClick : AuthEvent()
}
