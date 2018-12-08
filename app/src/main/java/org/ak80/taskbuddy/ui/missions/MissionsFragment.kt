package org.ak80.taskbuddy.ui.missions

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import dagger.android.support.DaggerFragment
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.di.ActivityScoped
import org.ak80.taskbuddy.ui.addeditmission.AddEditMissionActivity
import org.ak80.taskbuddy.ui.addeditmission.REQUEST_ADD_MISSION
import org.ak80.taskbuddy.ui.tasks.TasksActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.intentFor
import java.util.*
import javax.inject.Inject

/**
 * Display list of items
 */
@ActivityScoped
class MissionsFragment @Inject constructor() : DaggerFragment(), MissionsContract.View, AnkoLogger {

    @Inject
    lateinit var presenter: MissionsContract.Presenter

    private var listAdapter: MissionAdapter? = null

    private var missionsView: LinearLayout? = null

    private var missionsViewLabel: TextView? = null

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var missionsListener: MissionsListener = object : MissionsListener {

        override fun onMissionClick(clickedMission: Mission) {
            startActivity(intentFor<TasksActivity>(MISSION_ID to clickedMission.id))
        }

        override fun onMissionLongClick(clickedMission: Mission) {
            showMessage("long clicked on $clickedMission")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = MissionAdapter(ArrayList(0), missionsListener)
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
        val root = inflater.inflate(R.layout.missions_frag, container, false)
        setupMissionsView(root)

        setupFloatingActionButton()
        setHasOptionsMenu(true)
        setSwipeToRefresh(root)

        return root
    }


    private fun setupMissionsView(root: View) {
        missionsView = root.findViewById(R.id.missions_view)
        missionsViewLabel = missionsView!!.findViewById(R.id.missions_list_label)
        missionsViewLabel!!.setText(R.string.title_list_mission)
        val listView = root.findViewById<ListView>(R.id.missions_list)
        listView.adapter = listAdapter

    }

    private fun setupFloatingActionButton() {
        val fab = activity!!.findViewById<FloatingActionButton>(R.id.fab_add_mission)
        fab.setImageResource(R.drawable.ic_add)
        fab.setOnClickListener { presenter.addNewMission() }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.mission_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_clear -> presenter.clearMissions()
            R.id.menu_about -> presenter.about()
        }
        return true
    }

    override fun showMissions(missions: List<Mission>) {
        listAdapter!!.replaceData(missions)
        swipeRefreshLayout!!.isRefreshing = false
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

    private fun setSwipeToRefresh(root: View) {
        swipeRefreshLayout = root.findViewById<View>(R.id.refresh_layout_missions) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener { presenter.showMissions() }
    }

    override fun showAddMission() {
        // TODO TEST click fab start new activity
        startActivityForResult(intentFor<AddEditMissionActivity>(), REQUEST_ADD_MISSION)
    }

    interface MissionsListener {

        fun onMissionClick(clickedMission: Mission)

        fun onMissionLongClick(clickedMission: Mission)

    }

    private class MissionAdapter(missions: List<Mission>, private val missionsListener: MissionsListener) :
        BaseAdapter() {

        private var missions: List<Mission> = mutableListOf()

        init {
            setList(missions)
        }

        fun replaceData(missions: List<Mission>) {
            setList(missions)
            notifyDataSetChanged()
        }

        private fun setList(missions: List<Mission>) {
            this.missions = missions
        }

        override fun getCount(): Int {
            return missions.size
        }

        override fun getItem(i: Int): Mission {
            return missions[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val rowView: View? = getView(view, viewGroup)

            val mission = getItem(i)

            val title = rowView!!.findViewById<TextView>(R.id.title)
            title.text = mission.title

            val progress = rowView!!.findViewById<TextView>(R.id.progress)
            progress.text = "${mission.getCompletedTasks()} / ${mission.getTotalTasks()}"

            rowView.setOnClickListener { missionsListener.onMissionClick(mission) }

            rowView.setOnLongClickListener {
                missionsListener.onMissionLongClick(mission)
                true
            }
            return rowView
        }

        private fun getView(view: View?, viewGroup: ViewGroup): View? {
            var rowView: View? = view
            if (rowView == null) {
                val inflater = LayoutInflater.from(viewGroup.context)
                rowView = inflater.inflate(R.layout.mission_item, viewGroup, false)
            }
            return rowView
        }
    }

}