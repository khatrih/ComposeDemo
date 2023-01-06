package com.example.composedemo.features_note.data

sealed class Response<out T>(val data: T? = null, val error: Exception? = null) {
    class Loading<T>(val loading: Boolean) : Response<T>()
    class Success<out T>(data: T) : Response<T>(data = data)
    class Error<out T>(error: Exception) : Response<T>(error = error)
}

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}