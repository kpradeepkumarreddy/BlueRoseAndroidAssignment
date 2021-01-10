package com.pradeep.blueroseassignmentapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pradeep.blueroseassignmentapp.apis.FactApi
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import com.pradeep.blueroseassignmentapp.ui.fragments.FactsFragment
import javax.inject.Inject
import javax.inject.Singleton

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
@Singleton
class FactRepository @Inject constructor(
    private val factDB: FactRoomDB, private val factsApi: FactApi
) {
    private val errorCode = MutableLiveData<Int>()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    var factsFromDB: LiveData<Fact> = factDB.factDao().getFacts()
    val allFactItems: LiveData<List<FactItem>> = factDB.factItemDao().getFactItems()

    suspend fun getFacts() {
        try {
            // make the network request and get the data. And write the data to RoomDB
            val facts = factsApi.getFacts()
            Log.d("log", "factsApi response = $facts")
            // write the data to RoomDB
            insertToFactTable(Fact(facts.title))
            insertToFactItemsTable(facts.title, facts.rows)
        } catch (ex: Exception) {
            Log.e("log", "Exception in FactRepository::getFacts() = ", ex)
            errorCode.postValue(FactsFragment.ErrorCodes.NO_INTERNET.ordinal)
        }
    }

    private suspend fun insertToFactItemsTable(factTitle: String, factItems: List<FactItem>) {
        factItems.iterator().forEach {
            it.factTitle = factTitle
            factDB.factItemDao().insert(it)
        }
    }

    private suspend fun insertToFactTable(fact: Fact) {
        factDB.factDao().insert(fact)
    }

    fun getErrorStatus(): MutableLiveData<Int> {
        return errorCode
    }
}