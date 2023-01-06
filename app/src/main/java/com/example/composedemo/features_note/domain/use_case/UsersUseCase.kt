package com.example.composedemo.features_note.domain.use_case

import com.example.composedemo.features_note.domain.model.User
import com.example.composedemo.features_note.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UsersUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(user: User) {
        repository.insert(user)
    }

    suspend fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

    suspend fun signUpListener(email: String, password: String): Flow<User?> {
        return repository.signIn(email, password)
    }

    suspend fun verifyEmail(email: String): Flow<User?> {
        return repository.verifyEmail(email)
    }

}