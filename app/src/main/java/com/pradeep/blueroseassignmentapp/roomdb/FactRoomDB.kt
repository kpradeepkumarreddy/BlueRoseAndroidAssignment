package com.pradeep.blueroseassignmentapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pradeep.blueroseassignmentapp.roomdb.daos.FactDao
import com.pradeep.blueroseassignmentapp.roomdb.daos.FactItemDao
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

@Database(entities = [Fact::class, FactItem::class], version = 1, exportSchema = false)
abstract class FactRoomDB : RoomDatabase() {
    abstract fun factDao(): FactDao
    abstract fun factItemDao(): FactItemDao
}