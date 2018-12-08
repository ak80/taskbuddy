package org.ak80.taskbuddy.ui.missions

import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.ui.BasePresenter
import org.ak80.taskbuddy.ui.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface MissionsContract {

    interface View : BaseView {

        fun showMissions(missions: List<Mission>)

        fun showAbout(id: Int)

        fun showMessage(id: Int)

        fun showAddMission()

    }

    interface Presenter : BasePresenter<View> {

        fun showMissions()

        fun about()

        fun addNewMission()

        fun clearMissions()

    }
}
