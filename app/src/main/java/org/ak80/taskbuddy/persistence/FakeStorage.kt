package org.ak80.taskbuddy.persistence

import org.ak80.taskbuddy.core.model.Mission
import org.ak80.taskbuddy.core.model.Task

internal var tasksQiGong = listOf(
    Task(0, "Firework (1:00)"),
    Task(1, "Standing (2:00)"),
    Task(2, "Kwa Squat 10x normal"),
    Task(4, "Kwa Squat 10x extended"),
    Task(5, "Circling Hands 10x"),
    Task(6, "Circling Hands + Kwa 10x"),
    Task(7, "Hook the neck 2x"),
    Task(8, "D&T One arm L/R 10x "),
    Task(9, "D&T Both arms 10x "),
    Task(10, "D&T Footwork 10x "),
    Task(11, "Cloud Hands Weight Shift 5x"),
    Task(12, "Cloud Hands Turn 5x"),
    Task(13, "Cloud Hands Shift & Turn 5x"),
    Task(14, "Cloud Hands Arms L/R 5x")
)

internal var missions = listOf(
    Mission(0, "Qi Gong", tasksQiGong),
    Mission(1, "Meditation", listOf())
)
