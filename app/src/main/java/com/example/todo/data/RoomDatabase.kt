package com.example.todo.data


import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ToDo::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

abstract class ToDoDataBase : RoomDatabase() {
    abstract fun ToDoDao(): DAO
}

