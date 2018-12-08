package org.ak80.taskbuddy.ui.missions

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.ak80.taskbuddy.AcceptanceTest
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.aMission
import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.di.TaskBuddyApplication
import org.ak80.taskbuddy.listOf
import org.ak80.taskbuddy.persistence.MissionRepository
import org.ak80.taskbuddy.ui.utils.refreshView
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.core.Is.`is`
import org.junit.Test


class MissionsScreenTest : AcceptanceTest<MissionsActivity>(MissionsActivity::class.java) {

    var missionRepository: MissionRepository =
        (InstrumentationRegistry.getTargetContext().applicationContext as TaskBuddyApplication).getMissionRepository()

    @Test
    fun clickAddMissionButton_opensAddMissionUi() {
        // Click on the add mission button
        onView(withId(R.id.fab_add_mission)).perform(click())

        // Check if the add task screen is displayed
        onView(withId(R.id.add_mission_title)).check(matches(isDisplayed()))
    }

    // TODO more tests, see

    @Test
    fun start_showsMissionsView() {
        // Then
        onView(withId(R.id.missions_view)).check(matches(isDisplayed()))
    }

    @Test
    fun start_showsMissionsInListView() {
        // Given
        missionRepository.deleteAll()
        val missionList = listOf(2) { aMission() }
        missionRepository.addMission(missionList[0])
        missionRepository.addMission(missionList[1])

        // When
        refreshView(R.id.refresh_layout_missions)

        // Then
        onData(`is`(isMissionWithTitle(missionList[0].title))).check(matches(isCompletelyDisplayed()))
        onData(`is`(isMissionWithTitle(missionList[1].title))).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun pullToRefresh_shouldPass() {
        onView(withId(R.id.refresh_layout_missions)).perform(swipeDown())
    }


    private fun isMissionWithTitle(title: String): org.hamcrest.Matcher<Mission> {
        return object : BaseMatcher<Mission>() {

            override fun describeTo(description: Description?) {
                description!!.appendText("mission with title ").appendValue(title)
            }

            override fun matches(item: Any?): Boolean {
                if (item is Mission) {
                    return item.title == title
                }
                return false
            }

        }
    }

}

