package com.example.todo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(private val dao: DAO) {
    suspend fun addToDo(toDo: ToDo) {
        dao.addToDo(toDo)
    }

    suspend fun deleteToDo(todo: ToDo) {
        dao.deleteToDo(todo = todo)
    }

    suspend fun updateToDo(todo: ToDo) {
        dao.updateToDo(todo = todo)
    }

    suspend fun UpdateIsChecked(id: Long, checked : Boolean) {
        dao.UpdateIsChecked(checked, id)
    }

    fun getAllToDo(): Flow<List<ToDo>> {
        return dao.getAllToDo()
    }

    fun getToDo(id: Long): Flow<ToDo> {
        return dao.getToDo(id)
            .map {
                it ?: ToDo(0L, "", false, "")
            }
    }

    fun getByPriority(priority : String) : Flow<List<ToDo>> {
        return dao.getSep(priority)

    }


}