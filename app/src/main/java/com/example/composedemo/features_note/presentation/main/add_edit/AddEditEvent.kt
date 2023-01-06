package com.example.composedemo.features_note.presentation.main.add_edit

sealed class AddEditEvent {
    data class OnTitleChange(val title: String) : AddEditEvent()
    data class OnDescriptionChange(val description: String) : AddEditEvent()
    data class OnPriceChange(val price: String) : AddEditEvent()
    data class OnPayOptionChange(val payOption: String) : AddEditEvent()
    object OnSaveTodoClick : AddEditEvent()
    object OnPopBackStack : AddEditEvent()
}