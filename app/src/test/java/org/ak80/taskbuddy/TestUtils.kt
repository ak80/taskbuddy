package org.ak80.taskbuddy

import org.ak80.taskbuddy.Counter.count
import org.ak80.taskbuddy.core.model.Task

private object Counter {

    var count: Int = 1

}

fun anInt() = count++

fun aTitle() = "Title${anInt()}"

fun aTask(title: String = aTitle(), passed: Boolean = false): Task = Task(title, passed)