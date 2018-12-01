package org.ak80.taskbuddy.core.gateway

import org.ak80.taskbuddy.core.model.Task

/**
 * Gateway for [Task]s
 */
interface TaskGateway {

    fun loadTasks(callback: (List<Task>) -> Unit)

    fun addTask(task: Task)

    fun deleteAll()

}