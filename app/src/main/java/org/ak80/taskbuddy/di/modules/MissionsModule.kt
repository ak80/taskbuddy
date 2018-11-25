package org.ak80.taskbuddy.di.modules

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ak80.taskbuddy.core.gateway.MissionGateway
import org.ak80.taskbuddy.di.FragmentScoped
import org.ak80.taskbuddy.persistence.MissionRepository
import org.ak80.taskbuddy.ui.missions.MissionsContract
import org.ak80.taskbuddy.ui.missions.MissionsFragment
import org.ak80.taskbuddy.ui.missions.MissionsPresenter

/**
 * The Dagger module for Missions
 */
@Module
abstract class MissionsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun missionsFragment(): MissionsFragment

    @Binds
    internal abstract fun missionsPresenter(presenter: MissionsPresenter): MissionsContract.Presenter

    @Binds
    internal abstract fun missionRepository(missionRepository: MissionRepository): MissionGateway

}
