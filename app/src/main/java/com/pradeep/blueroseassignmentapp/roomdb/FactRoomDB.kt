package com.pradeep.blueroseassignmentapp.roomdb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pradeep.blueroseassignmentapp.R
import com.pradeep.blueroseassignmentapp.roomdb.daos.FactDao

abstract class FactRoomDB : RoomDatabase() {
    abstract fun factDao(): FactDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: FactRoomDB? = null

        fun getDatabase(context: Context): FactRoomDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FactRoomDB::class.java,
                    context.resources.getString(R.string.fact_database)
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}