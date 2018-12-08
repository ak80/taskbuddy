package org.ak80.taskbuddy.persistence

import android.content.Context
import android.database.Cursor
import org.ak80.taskbuddy.core.gateway.MissionGateway
import org.ak80.taskbuddy.core.model.Mission
import org.jetbrains.anko.db.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// TODO inject db directly
class MissionRepository @Inject constructor(val context: Context) : MissionGateway {

    //TODO in taskbuddydatabase machen!
    init {
        context.database.use {
            delete("Mission")
            delete("Task")

            insert("Mission", "title" to "Qi Gong")
            select("Mission", "id", "title").whereArgs("title = {title}", "title" to "Qi Gong").exec {
                val missionId = getMissionId()
                insertTask(missionId, "Firework (1:00)")
                insertTask(missionId, "Standing (2:00)")
                insertTask(missionId, "Kwa Squat 10x normal")
                insertTask(missionId, "Kwa Squat 10x extended")
                insertTask(missionId, "Circling Hands 10x")
                insertTask(missionId, "Circling Hands + Kwa 10x")
                insertTask(missionId, "Hook the neck 2x")
                insertTask(missionId, "D&T One arm L/R 10x ")
                insertTask(missionId, "D&T Both arms 10x ")
                insertTask(missionId, "D&T Footwork 10x ")
                insertTask(missionId, "Cloud Hands Weight Shift 5x")
                insertTask(missionId, "Cloud Hands Turn 5x")
                insertTask(missionId, "Cloud Hands Shift & Turn 5x")
                insertTask(missionId, "Cloud Hands Arms L/R 5x")
            }

            insert("Mission", "title" to "Meditation")
        }
    }

    private fun insertTask(missionId: Long, title: String) {
        context.database.use {
            insert("Task", "title" to title, "completed" to FALSE, "missionId" to missionId)
        }
    }

    private fun Cursor.getMissionId(): Long {
        return parseSingle(object : MapRowParser<Mission> {
            override fun parseRow(columns: Map<String, Any?>): Mission {
                val id = columns.getValue("id").toString().toLong()
                val title = columns.getValue("title").toString()
                return Mission(id, title, listOf())
            }
        }).id
    }

    override fun loadMissions(callback: (List<Mission>) -> Unit) {
        val missions =
            context.database.use {
                select("Mission", "id", "title")
                    .exec {
                        parseList(object : MapRowParser<Mission> {
                            override fun parseRow(columns: Map<String, Any?>): Mission {
                                val id = columns.getValue("id").toString().toLong()
                                val title = columns.getValue("title").toString()
                                return Mission(id, title, TaskRepository(context).getTasks(id))
                            }

                        })
                    }
            }
        callback.invoke(missions)
    }

    override fun saveMission(mission: Mission) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearPassed() {
        context.database.use {
            update("Task", "completed" to FALSE)
                .exec()
        }
    }

}
