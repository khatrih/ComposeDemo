package com.example.composedemo.feature_todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: TodoDao

    companion object {
        const val TODO_DATABASE_NAME = "todo_db"
    }
}