package org.ak80.taskbuddy.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.contrib.DrawerMatchers.isClosed
import android.support.test.espresso.contrib.DrawerMatchers.isOpen
import android.support.test.espresso.contrib.NavigationViewActions.navigateTo
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.view.Gravity
import org.ak80.taskbuddy.AcceptanceTest
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.ui.missions.MissionsActivity
import org.ak80.taskbuddy.ui.utils.getToolbarNavigationContentDescription
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppNavigationTest : AcceptanceTest<MissionsActivity>(MissionsActivity::class.java) {

    @Test
    fun clickOnInfoNavigationItem_ShowsInfoScreen() {
        openInfoScreen()

        onView(withId(R.id.info)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnListNavigationItem_ShowsMissionsScreen() {
        openInfoScreen()

        openMissionsScreen()

        onView(withId(R.id.missionsContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnAndroidHomeIcon_OpensNavigation() {
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT)))

        onView(
            withContentDescription(
                getToolbarNavigationContentDescription(testRule.activity, R.id.toolbar)
            )
        ).perform(click())

        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.LEFT)))
    }


    @Test
    fun Info_backNavigatesToMissions() {
        openInfoScreen()

        pressBack()

        onView(withId(R.id.missionsContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun backFromMissionsScreen_ExitsApp() {
        assertPressingBackExitsApp()
    }

    @Test
    fun backFromMissionsScreenAfterInfo_ExitsApp() {
        openInfoScreen()
        openMissionsScreen()

        assertPressingBackExitsApp()
    }

    private fun assertPressingBackExitsApp() {
        try {
            pressBack()
            fail("Should kill the app and throw an exception")
        } catch (e: NoActivityResumedException) {
            // Test OK
        }
    }

    private fun openMissionsScreen() {
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(open())

        onView(withId(R.id.nav_view))
            .perform(navigateTo(R.id.navigation_menu_item_missions))
    }

    private fun openInfoScreen() {
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(open())

        onView(withId(R.id.nav_view))
            .perform(navigateTo(R.id.navigation_menu_item_info))
    }
}