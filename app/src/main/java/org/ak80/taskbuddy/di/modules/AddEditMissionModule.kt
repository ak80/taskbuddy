package org.ak80.taskbuddy.di.modules

import android.support.annotation.Nullable
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.ak80.taskbuddy.core.gateway.MissionGateway
import org.ak80.taskbuddy.di.ActivityScoped
import org.ak80.taskbuddy.persistence.MissionRepository
import org.ak80.taskbuddy.ui.addeditmission.ARGUMENT_EDIT_MISSION_ID
import org.ak80.taskbuddy.ui.addeditmission.AddEditMissionActivity

/**
 * The Dagger module for Add/Edit Mission
 */
@Module
abstract class AddEditMissionModule {

    @Module
    object Static {
        @JvmStatic
        @Provides
        @ActivityScoped
        @Nullable
        internal fun provideMissionId(activity: AddEditMissionActivity): String? {
            return activity.intent.getStringExtra(ARGUMENT_EDIT_MISSION_ID)
        }
    }


    @Binds
    internal abstract fun missionRepository(missionRepository: MissionRepository): MissionGateway

}
