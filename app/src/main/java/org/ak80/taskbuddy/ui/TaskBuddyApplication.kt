package org.ak80.taskbuddy.ui

import android.support.annotation.VisibleForTesting
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.ak80.taskbuddy.di.DaggerAppComponent
import org.ak80.taskbuddy.persistence.TaskRepository
import javax.inject.Inject


class TaskBuddyApplication : DaggerApplication() {

    @Inject
    internal lateinit var taskRepository: TaskRepository

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    @VisibleForTesting
    fun getTasksRepository(): TaskRepository {
        return taskRepository
    }

}