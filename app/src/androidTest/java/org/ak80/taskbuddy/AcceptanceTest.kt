package org.ak80.taskbuddy

import android.app.Activity
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.ak80.taskbuddy.core.model.Task
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
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