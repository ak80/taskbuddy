package org.ak80.taskbuddy.core.gateway

import org.ak80.taskbuddy.core.model.Task

/**
 * Gateway for [Task]s
 */
interface TaskGateway {

    fun loadTasks(missionId: Long, callback: TaskGatewayCallback)

    fun clearPassed(missionId: Long)

    fun saveTask(completedTask: Task)

}

interface TaskGatewayCallback {


    fun callbackTasks(tasks: List<Task>)

    fun callbackTasksFailed()

}