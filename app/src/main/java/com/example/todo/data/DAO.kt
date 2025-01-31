package com.example.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DAO{
    //Adding a todo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addToDo(toDo: ToDo)
    //Deleting a todo
    @Delete
    abstract suspend fun deleteToDo(todo : ToDo)
    //Updating a todo
    @Update
    abstract suspend fun  updateToDo(todo : ToDo)

    @Query("update ToDo set CheckBox = :checked where id =:id ")
    abstract suspend fun UpdateIsChecked( checked: kotlin.Boolean, id: Long)

    //Get all todo
    @Query("select * from `ToDo`")
    abstract  fun getAllToDo() : Flow<List<ToDo>>
    //Get a todo by id
    @Query("select * from `ToDo` where id =:id")
    abstract  fun getToDo(id : Long) : Flow<ToDo>

    @Query("select * from `ToDo` where priority =:priority")
    abstract fun getSep(priority : String) : Flow<List<ToDo>>

}