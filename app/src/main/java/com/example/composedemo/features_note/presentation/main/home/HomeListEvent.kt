package com.example.composedemo.features_note.presentation.main.home

import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.model.User

sealed class HomeListEvent {
    data class OnDeletePackageClick(val packages: Packages) : HomeListEvent()
    data class OnDoneChange(val packages: Packages, val isDone: Boolean) : HomeListEvent()
    object OnUndoDeleteClick : HomeListEvent()
    data class OnPackageClick(val packages: Packages) : HomeListEvent()
    object OnAddPackageClick : HomeListEvent()
    data class OnDeleteUserClick(val user: User) : HomeListEvent()
    data class OnFilteringList(val txt: String): HomeListEvent()
}