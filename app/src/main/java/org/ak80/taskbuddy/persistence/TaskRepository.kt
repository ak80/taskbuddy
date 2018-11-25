package org.ak80.taskbuddy.persistence

import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.core.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() : TaskGateway {

    override fun loadTasks(callback: (List<Task>) -> Unit) {
        callback.invoke(tasks)
    }

    override fun clearAll() {
        tasks.forEach { task -> task.passed=false }
    }
}