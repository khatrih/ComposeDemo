package com.example.composedemo.features_note.data.data_source

import androidx.room.*
import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM User WHERE userEmail = :email")
    fun verifyEmail(email: String): Flow<User?>

    @Query("SELECT * FROM User WHERE userEmail = :email AND userPassword = :password")
    fun signIn(email: String, password: String): Flow<User?>

    @Delete
    suspend fun deleteUser(user: User)
}

@Dao
interface PackagesCrud {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPackage(packages: Packages)

    @Delete
    suspend fun deletePackage(packages: Packages)

    @Query("SELECT * FROM Packages WHERE pId = :id")
    suspend fun getPackageById(id: Int): Packages?

    @Query("SELECT * FROM Packages ")
    fun getPackages(): Flow<List<Packages>>


//    @Transaction
//    @Query("SELECT * FROM User")
//    fun getPackages(): Flow<List<UserWithProduct>>
//
//    @Transaction
//    @Query("SELECT * FROM User WHERE uId = :id")
//    suspend fun getPackageById(id: Int): UserWithProduct?

}