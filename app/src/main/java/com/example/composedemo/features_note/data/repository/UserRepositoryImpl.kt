package com.example.composedemo.features_note.data.repository

import com.example.composedemo.features_note.data.data_source.UserAuthDao
import com.example.composedemo.features_note.domain.model.User
import com.example.composedemo.features_note.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val dao: UserAuthDao) : UserRepository {

    override suspend fun insert(user: User) {
        Dispatchers.IO.apply { dao.insert(user) }
    }

    override suspend fun verifyEmail(email: String): Flow<User?> {
        return dao.verifyEmail(email)
    }

    override suspend fun signIn(email: String, password: String): Flow<User?> {
        return dao.signIn(email, password)
    }

    override suspend fun deleteUser(user: User) {
        dao.deleteUser(user)
    }
}