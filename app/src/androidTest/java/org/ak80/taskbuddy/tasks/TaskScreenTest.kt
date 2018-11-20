package org.ak80.taskbuddy.tasks

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.ak80.taskbuddy.AcceptanceTest
import org.ak80.taskbuddy.R
import org.junit.Test


class TaskScreenTest : AcceptanceTest<TasksActivity>(TasksActivity::class.java) {

    @Test
    fun start_showsTasksView() {
        // Then
        onView(withId(R.id.tasks_view)).check(matches(isDisplayed()))
    }

}