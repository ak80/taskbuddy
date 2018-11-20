package org.ak80.taskbuddy.repository

import org.ak80.taskbuddy.tasks.TaskGateway
import org.ak80.taskbuddy.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() : TaskGateway {

    var tasks = listOf(
        Task("Foo"),
        Task("Bar"),
        Task("Baz")
    )

    override fun loadTasks(callback: (List<Task>) -> Unit) {
        callback.invoke(tasks)
    }


}