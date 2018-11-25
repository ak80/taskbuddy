package org.ak80.taskbuddy.di.modules

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ak80.taskbuddy.di.FragmentScoped
import org.ak80.taskbuddy.ui.info.InfoContract
import org.ak80.taskbuddy.ui.info.InfoFragment
import org.ak80.taskbuddy.ui.info.InfoPresenter

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
