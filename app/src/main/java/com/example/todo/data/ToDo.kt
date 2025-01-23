package com.example.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDo")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "TodoTitle")
    var title : String = "",
    @ColumnInfo(name = "CheckBox")
    var isChecked : Boolean = false
)

