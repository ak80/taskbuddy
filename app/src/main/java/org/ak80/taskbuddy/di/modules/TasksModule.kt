package org.ak80.taskbuddy.di.modules

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ak80.taskbuddy.core.gateway.TaskGateway
import org.ak80.taskbuddy.di.FragmentScoped
import org.ak80.taskbuddy.persistence.MissionRepository
import org.ak80.taskbuddy.ui.tasks.TasksContract
import org.ak80.taskbuddy.ui.tasks.TasksFragment
import org.ak80.taskbuddy.ui.tasks.TasksPresenter

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
    internal abstract fun taskGateway(missionRepository: MissionRepository): TaskGateway

}
