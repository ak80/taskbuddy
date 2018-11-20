package org.ak80.taskbuddy.ui.info

import org.ak80.taskbuddy.ui.BasePresenter
import org.ak80.taskbuddy.ui.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface InfoContract {

    interface View : BaseView {

        fun setInfoText(id: Int)

    }

    interface Presenter : BasePresenter<View>

}
