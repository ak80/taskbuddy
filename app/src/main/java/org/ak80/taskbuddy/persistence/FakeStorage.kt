package org.ak80.taskbuddy.persistence

import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.core.model.Task

internal var tasks = listOf(
    Task("Foo"),
    Task("Bar"),
    Task("Baz")
)

internal var missions = listOf(
    Mission("M1", listOf()),
    Mission("M2", listOf())
)
