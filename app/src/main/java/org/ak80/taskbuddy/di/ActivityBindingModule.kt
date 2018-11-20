package org.ak80.taskbuddy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ak80.taskbuddy.ui.tasks.TasksActivity
import org.ak80.taskbuddy.ui.tasks.TasksModule
import org.ak80.taskbuddy.ui.info.InfoActivity
import org.ak80.taskbuddy.ui.info.InfoModule

/**
 * Define Activities here, and refer to their specific module
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TasksModule::class])
    internal abstract fun tasksActivity(): TasksActivity


    @ActivityScoped
    @ContributesAndroidInjector(modules = [InfoModule::class])
    internal abstract fun infoActivity(): InfoActivity

}