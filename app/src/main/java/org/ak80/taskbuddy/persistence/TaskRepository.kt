package org.ak80.taskbuddy.persistence

import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.core.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() : TaskGateway {

    private var taskList: MutableList<Task> = tasks.toMutableList()

    override fun loadTasks(callback: (List<Task>) -> Unit) {
        callback.invoke(taskList)
    }

    override fun addTask(task: Task) {
        taskList.add(task)
    }

    override fun deleteAll() {
        taskList.clear()
    }
}