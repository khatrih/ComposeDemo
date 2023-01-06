package com.example.composedemo.features_note.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

@Composable
fun Context.ShowToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}