package com.pradeep.blueroseassignmentapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

interface FactRepository {
    suspend fun insertToFactTable(fact: Fact)
    suspend fun insertToFactItemsTable(factItems: List<FactItem>)
    fun getFactTitle(): LiveData<Fact>
    fun getAllFactItems(): LiveData<List<FactItem>>
    suspend fun getFactsFromNetwork()
    fun getErrorStatus(): MutableLiveData<Int>
}