package com.example.composedemo.features_note.domain.repository

import com.example.composedemo.features_note.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insert(user: User)

    suspend fun verifyEmail(email: String): Flow<User?>

    suspend fun signIn(email: String, password: String): Flow<User?>

    suspend fun deleteUser(user: User)

}