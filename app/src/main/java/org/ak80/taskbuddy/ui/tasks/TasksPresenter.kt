package org.ak80.taskbuddy.ui.tasks

import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.core.model.Task
import org.ak80.taskbuddy.di.ActivityScoped
import javax.inject.Inject

/**
 * Presents [Task] s and listens to events from the [TasksFragment]
 */
@ActivityScoped
class TasksPresenter @Inject constructor(private var taskRepository: TaskGateway) : TasksContract.Presenter {

    private var tasksView: TasksContract.View? = null

    override fun takeView(view: TasksContract.View) {
        tasksView = view
        showTasks()
    }

    override fun dropView() {
        tasksView = null
    }

    override fun showTasks() {
        taskRepository.loadTasks { tasks -> tasksView?.showTasks(tasks) }
    }

    override fun completeTask(completedTask: Task) {
        completedTask.passed = true
        tasksView?.showTaskComplete()
    }

    override fun activateTask(activeTask: Task) {
        activeTask.passed = false
        tasksView?.showTaskActive()
    }

    override fun clearTasks() {
        taskRepository.deleteAll()
        showTasks()
    }

    override fun about() {
        tasksView?.showAbout(R.string.about)
    }

    override fun addNewTask() {
        tasksView?.showMessage(R.string.add_new_task)
    }

}
