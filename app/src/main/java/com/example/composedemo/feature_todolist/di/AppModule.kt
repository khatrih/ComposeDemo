package com.example.composedemo.feature_todolist.di

import android.app.Application
import androidx.room.Room
import com.example.composedemo.feature_todolist.data.TodoDatabase
import com.example.composedemo.feature_todolist.data.TodoDatabase.Companion.TODO_DATABASE_NAME
import com.example.composedemo.feature_todolist.data.TodoRepository
import com.example.composedemo.feature_todolist.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoAppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            TodoDatabase::class.java,
            TODO_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.dao)
    }

}