package org.ak80.taskbuddy.ui.addeditmission

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import dagger.android.support.DaggerAppCompatActivity
import org.ak80.taskbuddy.R
import javax.inject.Inject

/**
 * Add or edit screen for mission
 */

const val REQUEST_ADD_MISSION = 1

class AddEditMissionActivity : DaggerAppCompatActivity() {

    // TODO @Inject
    //TODO lateinit var addEditMissionFragmentProvider: Lazy<AddEditMissionFragment>

    @Inject
    @JvmField
    var missionId: String? = null

    private var actionBar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmission_act)

        setupToolbar()
        setToolbarTitle(missionId)

        // todo fragment
        // todo restore state

    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

    }

    // TODO TEST?
    private fun setToolbarTitle(missionId: String?) {
        if (missionId == null) {
            actionBar!!.setTitle(R.string.add_mission)
        } else {
            actionBar!!.setTitle(R.string.edit_mission)
        }
    }

    // TODO TEST
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    // Todo test fab
}