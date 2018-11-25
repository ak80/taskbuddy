package org.ak80.taskbuddy.ui.missions

import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.core.gateway.MissionGateway
import org.ak80.taskbuddy.di.ActivityScoped
import javax.inject.Inject

/**
 * Presents [Mission]s and listens to events from the [MissionsFragment]
 */
@ActivityScoped
class MissionsPresenter @Inject constructor(private var missionGateway: MissionGateway) : MissionsContract.Presenter {

    private var missionsView: MissionsContract.View? = null

    override fun takeView(view: MissionsContract.View) {
        missionsView = view
        showMissions()
    }

    override fun dropView() {
        missionsView = null
    }

    override fun showMissions() {
        missionGateway.loadMissions { missions -> missionsView?.showMissions(missions) }
    }

    override fun about() {
        missionsView?.showAbout(R.string.about)
    }

    override fun addNewMission() {
        missionsView?.showMessage(R.string.add_new_task)
    }

}
