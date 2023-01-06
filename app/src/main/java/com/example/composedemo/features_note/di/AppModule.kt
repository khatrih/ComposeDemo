package com.example.composedemo.features_note.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.composedemo.features_note.data.data_source.UserDatabase
import com.example.composedemo.features_note.data.data_source.UserDatabase.Companion.DATABASE_NAME
import com.example.composedemo.features_note.data.data_source.UserDatabase.Companion.MIGRATION_1_2

import com.example.composedemo.features_note.data.repository.PackageListRepositoryImpl
import com.example.composedemo.features_note.data.repository.UserRepositoryImpl
import com.example.composedemo.features_note.domain.repository.PackageListRepository
import com.example.composedemo.features_note.domain.repository.UserRepository
import com.example.composedemo.features_note.domain.use_case.PackageUseCase
import com.example.composedemo.features_note.domain.use_case.UsersUseCase
import com.example.composedemo.features_note.presentation.utils.PrefsUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app.applicationContext, UserDatabase::class.java, DATABASE_NAME
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: UserDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideUserUseCase(repository: UserRepository): UsersUseCase {
        return UsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePackageRepository(db: UserDatabase): PackageListRepository {
        return PackageListRepositoryImpl(db.packagesCrud)
    }

    @Provides
    @Singleton
    fun providePackageUseCase(repository: PackageListRepository): PackageUseCase {
        return PackageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context) = PrefsUtils(context)
}