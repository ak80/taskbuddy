package org.ak80.taskbuddy.core.model

/**
 * A Mission is a collection of Tasks
 */
data class Mission(val title: String, val tasks: List<Task>)