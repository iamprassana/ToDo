import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Graph
import com.example.todo.data.Repository
import com.example.todo.data.ToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppViewModel(private val todoRep : Repository = Graph.toDoRep) : ViewModel() {

    var title by mutableStateOf("")
    var isChecked by mutableStateOf(false)
    var priority by mutableStateOf("")
    fun onTitleChange(newTitle : String) {
        title = newTitle
    }

    fun onCheckedChange( newChecked : Boolean) {
       isChecked = newChecked
    }
    fun onPriorityChange(newPriority : String) {
        priority = newPriority
    }

    lateinit var mid : Flow<List<ToDo>>
    lateinit var high : Flow<List<ToDo>>
    lateinit var low : Flow<List<ToDo>>
    lateinit var todoes : Flow<List<ToDo>>
    init {
        viewModelScope.launch {
            todoes = todoRep.getAllToDo()
            high = todoRep.getByPriority("high")
            mid = todoRep.getByPriority("mid")
            low = todoRep.getByPriority("low")
        }
    }

    fun addToDo(todo: ToDo) {
        viewModelScope.launch {
            todoRep.addToDo(todo)
        }
    }

    fun deleteToDo(todo: ToDo) {
        viewModelScope.launch {
            todoRep.deleteToDo(todo)
        }
    }

    fun updateToDo(todo : ToDo) {
        viewModelScope.launch {
            todoRep.updateToDo(todo)
        }
    }

    fun UpdateChecked(id : Long, checked : Boolean) {
        viewModelScope.launch {
            todoRep.UpdateIsChecked(id = id, checked = checked)
        }
    }

    fun getToDoById(id : Long) : Flow<ToDo> {
        return todoRep.getToDo(id)
            .map { it ?: ToDo(0L, "", false,"") }
    }
}