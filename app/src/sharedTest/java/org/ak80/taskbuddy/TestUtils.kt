package org.ak80.taskbuddy

import org.ak80.taskbuddy.Counter.count
import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.core.model.Task

private object Counter {

    var count: Int = 1

}

fun few() = 6

fun some() = 12

fun many() = 24

fun <T> listOf(count: Int, builder: () -> T): List<T> {
    val list = mutableListOf<T>()
    for (i in 1..count) {
        list.add(builder.invoke())
    }
    return list
}

fun anInt() = count++

fun aTitle() = "Title${anInt()}"

fun aMission(title: String = aTitle(), list: List<Task> = listOf()) = Mission(title, list)

fun aTask(title: String = aTitle(), passed: Boolean = false) = Task(title, passed)