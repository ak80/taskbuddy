package org.ak80.taskbuddy.core.model

/**
 * A Task is a single thing to do
 */
data class Task(val id: Int, val title: String, var completed: Boolean = false)

