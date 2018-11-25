package org.ak80.taskbuddy.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.view.*
import android.widget.*
import dagger.android.support.DaggerFragment
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.core.model.Task
import org.ak80.taskbuddy.di.ActivityScoped
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*
import javax.inject.Inject

/**
 * Display list of items
 */
@ActivityScoped
class TasksFragment @Inject constructor() : DaggerFragment(), TasksContract.View, AnkoLogger {

    @Inject
    lateinit var presenter: TasksContract.Presenter

    private var listAdapter: TaskAdapter? = null

    private var tasksView: LinearLayout? = null

    private var tasksViewLabel: TextView? = null

    private var taskListener: TasksListener = object : TasksListener {

        override fun onTaskClick(clickedTask: Task) {
            showMessage("clicked on $clickedTask")
        }

        override fun onTaskLongClick(clickedTask: Task) {
            showMessage("long clicked on $clickedTask")
        }

        override fun onCompleteTaskClick(completedTask: Task) {
            presenter.completeTask(completedTask)
        }

        override fun onActivateTaskClick(activatedTask: Task) {
            presenter.activateTask(activatedTask)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = TaskAdapter(ArrayList(0), taskListener)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        info("on result with requestCode=$requestCode, resultCode=$resultCode from intent=$data")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.tasks_frag, container, false)
        setupTasksView(root)

        setupFloatingActionButton()
        setHasOptionsMenu(true)

        return root
    }


    private fun setupTasksView(root: View) {
        tasksView = root.findViewById(R.id.tasks_view)
        tasksViewLabel = tasksView!!.findViewById(R.id.tasks_list_label)
        tasksViewLabel!!.setText(R.string.title_list_tasks)
        val listView = root.findViewById<ListView>(R.id.tasks_list)
        listView.adapter = listAdapter

    }

    private fun setupFloatingActionButton() {
        val fab = activity!!.findViewById<FloatingActionButton>(R.id.fab_add_task)
        fab.setImageResource(R.drawable.ic_add)
        fab.setOnClickListener { presenter.addNewTask() }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.task_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_clear -> presenter.clearTask()
            R.id.menu_about -> presenter.about()
        }
        return true
    }

    override fun showTasks(tasks: List<Task>) {
        listAdapter!!.replaceData(tasks)
    }

    override fun showTaskComplete() {
        showMessage(getString(R.string.task_marked_complete))
    }

    override fun showTaskActive() {
        showMessage(getString(R.string.task_marked_active))
    }

    override fun showAbout(id: Int) {
        showMessage(getString(id))
    }

    override fun showMessage(id: Int) {
        showMessage(getString(id))
    }

    private fun showMessage(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    interface TasksListener {

        fun onTaskClick(clickedTask: Task)

        fun onTaskLongClick(clickedTask: Task)

        fun onCompleteTaskClick(completedTask: Task)

        fun onActivateTaskClick(activatedTask: Task)

    }

    private class TaskAdapter(tasks: List<Task>, private val tasksListener: TasksListener) : BaseAdapter() {

        private var tasks: List<Task> = mutableListOf()

        init {
            setList(tasks)
        }

        fun replaceData(tasks: List<Task>) {
            setList(tasks)
            notifyDataSetChanged()
        }

        private fun setList(tasks: List<Task>) {
            this.tasks = tasks
        }

        override fun getCount(): Int {
            return tasks.size
        }

        override fun getItem(i: Int): Task {
            return tasks[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val rowView: View? = getView(view, viewGroup)

            val task = getItem(i)

            val titleTV = rowView!!.findViewById<TextView>(R.id.title)
            titleTV.text = task.title

            val completeCB = rowView.findViewById<CheckBox>(R.id.complete)
            activateCheckBoxInView(completeCB, task, rowView, viewGroup)
            setOnClickListenerForCheckBox(completeCB, task)

            rowView.setOnClickListener { tasksListener.onTaskClick(task) }

            rowView.setOnLongClickListener {
                tasksListener.onTaskLongClick(task)
                true
            }
            return rowView
        }

        private fun setOnClickListenerForCheckBox(completeCB: CheckBox, task: Task) {
            completeCB.setOnClickListener {
                if (!task.passed) {
                    tasksListener.onCompleteTaskClick(task)
                } else {
                    tasksListener.onActivateTaskClick(task)
                }
            }
        }

        private fun activateCheckBoxInView(completeCB: CheckBox, task: Task, rowView: View, viewGroup: ViewGroup) {
            completeCB.isChecked = task.passed
            if (task.passed) {
                rowView.background = ResourcesCompat.getDrawable(
                    viewGroup.context.resources,
                    R.drawable.list_completed_touch_feedback,
                    null
                )
            } else {
                rowView.background = ResourcesCompat.getDrawable(
                    viewGroup.context.resources,
                    R.drawable.touch_feedback, null
                )
            }
        }

        private fun getView(view: View?, viewGroup: ViewGroup): View? {
            var rowView: View? = view
            if (rowView == null) {
                val inflater = LayoutInflater.from(viewGroup.context)
                rowView = inflater.inflate(R.layout.task_item, viewGroup, false)
            }
            return rowView
        }
    }

}