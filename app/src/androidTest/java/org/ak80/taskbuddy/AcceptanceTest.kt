package org.ak80.taskbuddy

import android.app.Activity
import android.support.test.espresso.Espresso
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.ak80.taskbuddy.core.model.Task
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.anything
import org.junit.Rule
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class AcceptanceTest<T : Activity>(clazz: Class<T>) {

    @Rule
    @JvmField
    val testRule: ActivityTestRule<T> = IntentsTestRule(clazz)

    val checkThat: Matchers = Matchers()

    fun matchesTask(title: String): Matcher<out Any>? {
        return TaskWithTitleMatcher(title)
    }

}

internal class TaskWithTitleMatcher(private val title: String?) : BaseMatcher<Any>() {

    override fun describeTo(description: Description?) {
        description?.appendText("should be an Task with title=$title")
    }

    override fun matches(item: Any?) = item is Task && item.title == title

}

class WaitAction(val condition: Matcher<View>, val timeoutMs: Long) : ViewAction {


    override fun getConstraints(): Matcher<View> {
        return anything() as Matcher<View>
    }

    override fun getDescription(): String {
        return "wait";
    }


    override fun perform(uiController: UiController?, view: View?) {
        uiController!!.loopMainThreadUntilIdle()

        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeoutMs

        while (System.currentTimeMillis() < endTime) {
            if (condition.matches(view)) {
                return
            }

            uiController!!.loopMainThreadForAtLeast(100)
        }
        throw PerformException.Builder().withViewDescription("timeout").build()
    }

}

fun waitFor(condition: Matcher<View>, timeout: Long): ViewAction {
    return WaitAction(condition, timeout)
}


fun refreshView(id: Int) {
    Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.swipeDown())
}