package com.pradeep.blueroseassignmentapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pradeep.blueroseassignmentapp.apis.FactApi
import com.pradeep.blueroseassignmentapp.models.Facts
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import com.pradeep.blueroseassignmentapp.roomdb.entities.relations.FactWithItems
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FactRepository(private val factDB: FactRoomDB) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val factsApi = retrofit.create(FactApi::class.java)

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    var factsFromDB: LiveData<Fact> = factDB.factDao().getFacts()
    val allFactItems: LiveData<List<FactItem>> = factDB.factItemDao().getFactItems()

    suspend fun getFacts() {
        // make the network request and get the data. And write the data to RoomDB
        val facts = factsApi.getFacts()
        Log.d("log", "factsApi response = $facts")
        // write the data to RoomDB
        insertToFactTable(Fact(facts.title))
        insertToFactItemsTable(facts.title, facts.rows)
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
}