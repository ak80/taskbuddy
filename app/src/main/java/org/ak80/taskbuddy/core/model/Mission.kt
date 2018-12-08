package org.ak80.taskbuddy.core.model

/**
 * A Mission is a collection of Tasks
 */
data class Mission(val id: Long, val title: String, val tasks: List<Task> = listOf()) {

    fun getCompletedTasks() = tasks.filter { task -> task.completed }.count()

    fun getTotalTasks() = tasks.size

}