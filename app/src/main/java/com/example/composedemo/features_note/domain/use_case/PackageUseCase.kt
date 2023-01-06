package com.example.composedemo.features_note.domain.use_case

import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.repository.PackageListRepository
import kotlinx.coroutines.flow.Flow

class PackageUseCase(private val repository: PackageListRepository) {

    suspend fun insertPackage(packages: Packages) {
        repository.insertPackage(packages)
    }

    suspend fun deletePackages(packages: Packages) {
        repository.deletePackages(packages)
    }

    fun getPackages(): Flow<List<Packages>> {
        return repository.getPackages()
    }

    suspend fun getPackageById(id: Int): Packages? {
        return repository.getTodoById(id)
    }

    /*fun getPackages(): Flow<List<UserWithProduct>> {
        return repository.getPackages()
    }

    suspend fun getPackageById(id: Int): UserWithProduct? {
        return repository.getTodoById(id)
    }*/


}