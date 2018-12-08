package org.ak80.taskbuddy.ui.utils

import android.app.Activity
import android.support.annotation.IdRes
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.Toolbar
import android.view.View


fun refreshView(id: Int) {
    Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.swipeDown())
}

fun getToolbarNavigationContentDescription(activity: Activity, @IdRes toolbar1: Int): String? {
    val toolbar = activity.findViewById<View>(toolbar1) as Toolbar?
    return if (toolbar != null) {
        toolbar.navigationContentDescription as String?
    } else {
        throw RuntimeException("No toolbar found.")
    }
}