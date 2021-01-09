package com.pradeep.blueroseassignmentapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pradeep.blueroseassignmentapp.R
import com.pradeep.blueroseassignmentapp.roomdb.daos.FactDao
import com.pradeep.blueroseassignmentapp.roomdb.daos.FactItemDao
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

@Database(entities = [Fact::class, FactItem::class], version = 1, exportSchema = false)
abstract class FactRoomDB : RoomDatabase() {
    abstract fun factDao(): FactDao
    abstract fun factItemDao(): FactItemDao

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