package com.pradeep.blueroseassignmentapp

import android.app.Application
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB

class FactsApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { FactRoomDB.getDatabase(this) }
    val repository by lazy { FactRepository(database.factDao()) }
}