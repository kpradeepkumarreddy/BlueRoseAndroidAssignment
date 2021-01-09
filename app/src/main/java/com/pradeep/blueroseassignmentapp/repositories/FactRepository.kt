package com.pradeep.blueroseassignmentapp.repositories

import androidx.annotation.WorkerThread
import com.pradeep.blueroseassignmentapp.roomdb.daos.FactDao
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FactRepository(private val factDao: FactDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFacts: Flow<List<FactItem>> = factDao.getFacts()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(factItem: FactItem) {
        factDao.insert(factItem)
    }
}