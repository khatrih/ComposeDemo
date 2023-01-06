package com.example.composedemo.features_note.data.repository

import com.example.composedemo.features_note.data.data_source.PackagesCrud
import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.repository.PackageListRepository
import kotlinx.coroutines.flow.Flow

class PackageListRepositoryImpl(private val dao: PackagesCrud) : PackageListRepository {

    override suspend fun insertPackage(packages: Packages) {
        dao.insertPackage(packages)
    }

    override suspend fun deletePackages(packages: Packages) {
        dao.deletePackage(packages)
    }

    /*override fun getPackages(): Flow<List<UserWithProduct>> {
        return dao.getPackages()
    }

    override suspend fun getTodoById(id: Int): UserWithProduct? {
        return dao.getPackageById(id)
    }*/

    override suspend fun getTodoById(id: Int): Packages? {
        return dao.getPackageById(id)
    }

    override fun getPackages(): Flow<List<Packages>> {
        return dao.getPackages()
    }
}