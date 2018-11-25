package org.ak80.taskbuddy.ui.tasks

import org.ak80.taskbuddy.ui.BasePresenter
import org.ak80.taskbuddy.ui.BaseView
import org.ak80.taskbuddy.core.model.Task

/**
 * This specifies the contract between the view and the presenter.
 */
interface TasksContract {

    interface View : BaseView {

        fun showTasks(tasks: List<Task>)

        fun showTaskComplete()

        fun showTaskActive()

        fun showAbout(id: Int)

        fun showMessage(id: Int)

    }

    interface Presenter : BasePresenter<View> {

        fun showTasks()

        fun completeTask(completedTask: Task)

        fun activateTask(activeTask: Task)

        fun clearTask()

        fun about()

        fun addNewTask()

    }
}
