package com.example.composedemo.features_note.domain.repository

import com.example.composedemo.features_note.domain.model.Packages
import kotlinx.coroutines.flow.Flow

interface PackageListRepository {

    suspend fun insertPackage(packages: Packages)

    suspend fun deletePackages(packages: Packages)

    suspend fun getTodoById(id: Int): Packages?

    fun getPackages(): Flow<List<Packages>>

    //fun getPackages(): Flow<List<UserWithProduct>>

    //suspend fun getTodoById(id: Int): UserWithProduct?
}