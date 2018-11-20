package org.ak80.taskbuddy.info

import org.ak80.taskbuddy.BasePresenter
import org.ak80.taskbuddy.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface InfoContract {

    interface View : BaseView {

        fun setInfoText(id: Int)

    }

    interface Presenter : BasePresenter<View>

}
