package org.ak80.taskbuddy.info

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ak80.taskbuddy.di.FragmentScoped

/**
 * The dagger module for Info
 */
@Module
abstract class InfoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun infoFragment(): InfoFragment

    @Binds
    internal abstract fun infoPresenter(presenter: InfoPresenter): InfoContract.Presenter

}
