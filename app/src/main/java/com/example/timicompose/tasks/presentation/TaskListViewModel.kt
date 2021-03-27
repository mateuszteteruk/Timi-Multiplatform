package com.example.timicompose.tasks.presentation

import androidx.lifecycle.ViewModel
import com.example.timicompose.tasks.data.TaskRepository
import com.example.timicompose.tasks.presentation.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasks: MutableStateFlow<List<Task>> = MutableStateFlow(taskRepository.tasks)

    fun toggleTask(toggledTask: Task) {
        val newTasks = tasks.value.map { task ->
            if (task == toggledTask) {
                task.copy(isSelected = task.isSelected.not())
            } else {
                task
            }
        }
        tasks.value = newTasks
    }

    fun deleteTasks(tasksToBeDeleted: List<Task>) {
        val newTasks = tasks.value.filterNot { task -> tasksToBeDeleted.contains(task) }
        tasks.value = newTasks
    }

    fun addTask(taskToBeAdded: Task) {
        val newTasks = tasks.value.toMutableList()
        newTasks.add(taskToBeAdded)
        tasks.value = newTasks
    }
}
