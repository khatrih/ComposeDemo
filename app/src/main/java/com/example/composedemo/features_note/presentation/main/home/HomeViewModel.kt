package com.example.composedemo.features_note.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.use_case.PackageUseCase
import com.example.composedemo.features_note.presentation.utils.Routes
import com.example.composedemo.features_note.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: PackageUseCase) : ViewModel() {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val getPackages = useCase.getPackages()

    private var deletedPackages: Packages? = null

    fun onEvent(event: HomeListEvent) {
        when (event) {
            is HomeListEvent.OnAddPackageClick -> {
                sendUiEvent(UIEvent.Navigate(Routes.ROUTE_ADD_EDIT))
            }
            is HomeListEvent.OnDeletePackageClick -> {
                viewModelScope.launch {
                    deletedPackages = event.packages
                    useCase.deletePackages(event.packages)
                    sendUiEvent(
                        UIEvent.ShowSnackbar(
                            message = "package deleted",
                            action = "Undo"
                        )
                    )
                }
            }
            is HomeListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    useCase.insertPackage(
                        event.packages.copy(isDone = event.isDone)
                    )
                }
            }
            is HomeListEvent.OnPackageClick -> {
                viewModelScope.launch {
                    sendUiEvent(UIEvent.Navigate(Routes.ROUTE_ADD_EDIT + "?todoId=${event.packages.pId}"))
                }
            }
            is HomeListEvent.OnUndoDeleteClick -> {
                deletedPackages?.let { packages ->
                    viewModelScope.launch {
                        useCase.insertPackage(packages)
                    }
                }
            }
            else -> Unit
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}