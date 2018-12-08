package org.ak80.taskbuddy.ui.tasks

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.ak80.taskbuddy.AcceptanceTest
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.aTask
import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.core.model.Task
import org.ak80.taskbuddy.di.TaskBuddyApplication
import org.ak80.taskbuddy.listOf
import org.ak80.taskbuddy.ui.utils.refreshView
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.core.Is
import org.junit.Test


class TaskScreenTest : AcceptanceTest<TasksActivity>(TasksActivity::class.java) {

    var taskRepository: TaskGateway =
        (InstrumentationRegistry.getTargetContext().applicationContext as TaskBuddyApplication).getMissionRepository()


    @Test
    fun start_showsTasksView() {
        // Then
        onView(withId(R.id.tasks_view)).check(matches(isDisplayed()))
    }

    @Test
    fun start_showsTasksInListView() {
        // Given
        taskRepository.clearPassed(1L)
        val taskList = listOf(2) { aTask() }
        taskRepository.saveTask(taskList[0])
        taskRepository.saveTask(taskList[1])

        // When
        refreshView(R.id.refresh_layout_tasks)

        // Then
        onData(Is.`is`(isTaskWithTitle(taskList[0].title)))
            .check(matches(ViewMatchers.isCompletelyDisplayed()))
        onData(Is.`is`(isTaskWithTitle(taskList[1].title)))
            .check(matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun pullToRefresh_shouldPass() {
        onView(withId(R.id.refresh_layout_tasks)).perform(ViewActions.swipeDown())
    }

    private fun isTaskWithTitle(title: String): org.hamcrest.Matcher<Task> {
        return object : BaseMatcher<Task>() {

            override fun describeTo(description: Description?) {
                description!!.appendText("task with title ").appendValue(title)
            }

            override fun matches(item: Any?): Boolean {
                if (item is Task) {
                    return item.title == title
                }
                return false
            }

        }
    }

}