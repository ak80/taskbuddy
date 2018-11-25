package org.ak80.taskbuddy.persistence

import org.ak80.taskbuddy.core.gateway.MissionGateway
import org.ak80.taskbuddy.core.model.Mission
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MissionRepository @Inject constructor() : MissionGateway {


    override fun loadMissions(callback: (List<Mission>) -> Unit) {
        callback.invoke(missions)
    }
}