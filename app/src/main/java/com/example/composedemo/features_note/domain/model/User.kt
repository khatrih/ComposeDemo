package com.example.composedemo.features_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uId: Int? = null,
    val userName: String,
    val userEmail: String,
    val userPassword: String,
)