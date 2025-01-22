package com.example.todo.data


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities =[ToDo :: class],
    version = 1,
    exportSchema = false
)

abstract class ToDoDataBase : RoomDatabase() {
    abstract fun ToDoDao() : DAO
}
