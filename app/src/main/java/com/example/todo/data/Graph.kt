package com.example.todo.data

import android.content.Context
import androidx.room.Room

object Graph {

    lateinit var todoDatabase : ToDoDataBase

    val toDoRep by lazy {
        Repository(dao = todoDatabase.ToDoDao())
    }

    fun provide(context: Context) {
        todoDatabase = Room.databaseBuilder(context, ToDoDataBase::class.java, "todo.db").build()
    }

}