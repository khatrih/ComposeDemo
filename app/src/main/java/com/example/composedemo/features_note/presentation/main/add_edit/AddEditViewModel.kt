package com.example.composedemo.features_note.presentation.main.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.use_case.PackageUseCase
import com.example.composedemo.features_note.presentation.utils.PrefsUtils
import com.example.composedemo.features_note.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val userCase: PackageUseCase,
    savedStateHandle: SavedStateHandle,
    private val prefsUtils: PrefsUtils
) : ViewModel() {

    var packages by mutableStateOf<Packages?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var userId by mutableStateOf(0)

    var payOption by mutableStateOf("")

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            prefsUtils.authToken.collect { id ->
                id?.let { userId = it }
            }
        }
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                userCase.getPackageById(todoId)?.let { packages ->
                    title = packages.title
                    price = packages.price
                    description = packages.description ?: ""
                    payOption = packages.payOption
                    this@AddEditViewModel.packages = packages
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditEvent.OnPriceChange -> {
                price = event.price
            }
            is AddEditEvent.OnPayOptionChange -> {
                payOption = event.payOption
            }
            is AddEditEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(UIEvent.ShowSnackbar(message = "The title can't be empty"))
                        return@launch
                    }
                    if (price.isBlank()) {
                        sendUiEvent(UIEvent.ShowSnackbar(message = "The price can't be empty"))
                        return@launch
                    }
                    if (payOption.isBlank()) {
                        sendUiEvent(UIEvent.ShowSnackbar(message = "The payOption can't be empty"))
                        return@launch
                    }

                    userCase.insertPackage(
                        packages = Packages(
                            title = title,
                            description = description,
                            isDone = packages?.isDone ?: false,
                            pId = packages?.pId,
                            price = price,
                            userId = userId,
                            payOption = payOption,
                            updates = userId
                        )
                    )
                    sendUiEvent(UIEvent.PopBackStack)
                }
            }
            AddEditEvent.OnPopBackStack -> {
                sendUiEvent(UIEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}