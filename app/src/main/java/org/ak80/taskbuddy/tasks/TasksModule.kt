package org.ak80.taskbuddy.tasks

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ak80.taskbuddy.di.FragmentScoped
import org.ak80.taskbuddy.repository.TaskRepository

/**
 * The Dagger module for Tasks
 */
@Module
abstract class TasksModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun tasksFragment(): TasksFragment

    @Binds
    internal abstract fun tasksPresenter(presenter: TasksPresenter): TasksContract.Presenter

    @Binds
    internal abstract fun tasksRepository(taskRepository: TaskRepository): TaskGateway

}
