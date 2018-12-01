package org.ak80.taskbuddy.ui.missions

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.aMission
import org.ak80.taskbuddy.core.gateway.MissionGateway
import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.listOf
import org.ak80.taskbuddy.some
import org.junit.Before
import org.junit.Test

class MissionsPresenterTest {

    private lateinit var presenter: MissionsPresenter

    @RelaxedMockK
    lateinit var missionRepository: MissionGateway

    @RelaxedMockK
    lateinit var view: MissionsContract.View

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter = MissionsPresenter(missionRepository)
    }

    @Test
    fun loadAllTasksFromRepositoryAndLoadIntoView_whenTakeView() {
        // Given
        val missionList = listOf(some()) { aMission() }
        missionRepository.answerLoadCallbackWith(missionList)

        // When
        presenter.takeView(view)

        // Then
        verify { view.showMissions(missionList) }
    }

    @Test
    fun showAboutInView_whenAbout() {
        // Given
        val missionList = listOf(some()) { aMission() }
        missionRepository.answerLoadCallbackWith(missionList)
        presenter.takeView(view)

        // When
        presenter.about()

        // Then
        verify { view.showAbout(R.string.about) }
    }

    @Test
    fun showMessageInView_whenAddNewTask() {
        // Given
        val missionList = listOf(some()) { aMission() }
        missionRepository.answerLoadCallbackWith(missionList)
        presenter.takeView(view)

        // When
        presenter.addNewMission()

        // Then
        verify { view.showMessage(R.string.add_new_task) }
    }

    @Test
    fun doNotShowMissionsAgain_whenDropView() {
        // Given
        val missionList = listOf(some()) { aMission() }
        missionRepository.answerLoadCallbackWith(missionList)
        presenter.takeView(view)

        // When
        presenter.dropView()
        presenter.showMissions()


        // Then
        verify { view.showMissions(missionList) }
    }


    private fun MissionGateway.answerLoadCallbackWith(missionList: List<Mission>) {
        val loadCallback = slot<(List<Mission>) -> Unit>()
        every {
            loadMissions(capture(loadCallback))
        } answers {
            loadCallback.captured.invoke(missionList)
        }
    }

}