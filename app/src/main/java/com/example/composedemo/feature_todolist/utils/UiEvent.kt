package com.example.composedemo.feature_todolist.utils

sealed class UiEvent {
    object PopBackStack : UiEvent() //for back stack
    data class Navigate(val route: String) : UiEvent() //for navigate the screens
    data class ShowSnackbar(
        val message: String, val action: String? = null,
    ) : UiEvent()//for showing snackbar also some action are perform like: undo
}
