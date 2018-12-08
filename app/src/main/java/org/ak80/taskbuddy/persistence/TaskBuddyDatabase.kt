package org.ak80.taskbuddy.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

const val TRUE = 1
const val FALSE = 0

//TODO reset versioN of db!
class TaskBuddyDatabase(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "TaskDatabase", null, 1) {
    companion object {
        private var instance: TaskBuddyDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): TaskBuddyDatabase {
            if (instance == null) {
                instance = TaskBuddyDatabase(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            "Mission", true,
            "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "title" to TEXT
        )
        db.createTable(
            "Task", true,
            "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "title" to TEXT,
            "completed" to INTEGER,
            "missionId" to INTEGER,
            FOREIGN_KEY("id", "Mission", "missionId")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}

val Context.database: TaskBuddyDatabase
    get() = TaskBuddyDatabase.getInstance(getApplicationContext())