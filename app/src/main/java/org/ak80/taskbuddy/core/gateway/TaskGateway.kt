package org.ak80.taskbuddy.core.gateway

import org.ak80.taskbuddy.core.model.Task

/**
 * Gateway for [Task]s
 */
interface TaskGateway {

    fun loadTasks(missionId: Int, callback: TaskGatewayCallback)

    fun clearPassed(missionId: Int)

    fun saveTask(completedTask: Task)

}

interface TaskGatewayCallback {


    fun callbackTasks(tasks: List<Task>)

    fun callbackTasksFailed()

}