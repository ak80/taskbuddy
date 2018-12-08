package org.ak80.taskbuddy.ui.tasks

import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.core.gateway.TaskGatewayCallback
import org.ak80.taskbuddy.core.model.Task
import org.ak80.taskbuddy.di.ActivityScoped
import javax.inject.Inject

/**
 * Presents [Task] s and listens to events from the [TasksFragment]
 */
@ActivityScoped
class TasksPresenter @Inject constructor(private var taskRepository: TaskGateway) : TasksContract.Presenter,
    TaskGatewayCallback {

    private var tasksView: TasksContract.View? = null

    private var missionId: Long = -1

    override fun takeView(view: TasksContract.View) {
        tasksView = view
    }

    override fun dropView() {
        tasksView = null
    }


    override fun showTasks(missionId: Long) {
        this.missionId = missionId
        taskRepository.loadTasks(missionId, this)

    }

    override fun callbackTasks(tasks: List<Task>) {
        tasksView?.showTasks(tasks)
    }

    override fun callbackTasksFailed() {
        tasksView?.showMessage(R.string.load_tasks_failed)
    }


    override fun completeTask(completedTask: Task) {
        completedTask.completed = true
        taskRepository.saveTask(completedTask)
        tasksView?.showTaskComplete()
    }

    override fun activateTask(activeTask: Task) {
        activeTask.completed = false
        taskRepository.saveTask(activeTask)
        tasksView?.showTaskActive()
    }

    override fun clearTasks() {
        taskRepository.clearPassed(missionId)
        tasksView?.showTaskActive()
    }

    override fun about() {
        tasksView?.showAbout(R.string.about)
    }

    override fun addNewTask() {
        tasksView?.showMessage(R.string.add_new_task)
    }

}
