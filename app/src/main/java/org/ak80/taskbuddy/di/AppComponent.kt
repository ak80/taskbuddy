package org.ak80.taskbuddy.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.ak80.taskbuddy.TaskBuddyApplication

import javax.inject.Singleton

/**
 * The Dagger component for [TaskBuddyApplication] add new activities in [AndroidSupportInjectionModule]
 */
@Singleton
@Component(
    modules = [ApplicationModule::class, ActivityBindingModule::class, AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<TaskBuddyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}