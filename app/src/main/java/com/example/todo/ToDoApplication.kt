package com.example.todo

import android.app.Application
import com.example.todo.data.Graph

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}