package org.ak80.taskbuddy.core.gateway

import org.ak80.taskbuddy.core.model.Mission

/**
 * Gateway for [Mission]s
 */
interface MissionGateway {

    fun loadMissions(callback: (List<Mission>) -> Unit)

}