package com.example.todo.data

import kotlinx.coroutines.flow.Flow

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
        return dao.getToDo(id = id)
    }

}