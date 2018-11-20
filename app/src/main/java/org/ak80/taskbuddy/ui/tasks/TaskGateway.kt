package org.ak80.taskbuddy.ui.tasks

import org.ak80.taskbuddy.model.Task

/**
 * Gateway for [Task]s
 */
interface TaskGateway {

    fun loadTasks(callback: (List<Task>) -> Unit)

    fun clearAll()

}