package org.ak80.taskbuddy.persistence

import android.content.Context
import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.core.gateway.TaskGatewayCallback
import org.ak80.taskbuddy.core.model.Task
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// TODO inject db directly
class TaskRepository @Inject constructor(val context: Context) : TaskGateway {

    override fun loadTasks(missionId: Int, callback: TaskGatewayCallback) {
        callback.callbackTasks(getTasks(missionId))
    }

    fun getTasks(missionId: Int): List<Task> {
        val tasks = context.database.use {
            select("Task").whereArgs("missionId = {missionId}", "missionId" to missionId)
                .exec {
                    parseList(object : MapRowParser<Task> {
                        override fun parseRow(columns: Map<String, Any?>): Task {
                            val id = columns.getValue("id").toString().toInt()
                            val title = columns.getValue("title").toString()
                            val passed = columns.getValue("completed").toString().toInt() == TRUE
                            return Task(id, title, passed)
                        }

                    })
                }
        }
        return tasks
    }


    override fun saveTask(task: Task) {
        // todo Insert or Update?
        val completed: Int = if (task.completed) TRUE else FALSE

        context.database.use {
            update("Task", "completed" to completed)
                .whereArgs("id = {id}", "id" to task.id)
                .exec()
        }
    }

    override fun clearPassed(missionId: Int) {
        context.database.use {
            update("Task", "completed" to FALSE)
                .whereArgs("missionId = {missionId}", "missionId" to missionId)
                .exec()
        }
    }
}