package com.pradeep.blueroseassignmentapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import com.pradeep.blueroseassignmentapp.ui.fragments.FactsFragment
import com.pradeep.blueroseassignmentapp.utils.Constants

class FakeFactRepositoryImpl : FactRepository {
    private val factItems = mutableListOf<FactItem>()
    private lateinit var fact: Fact
    private val observableFactItems = MutableLiveData<List<FactItem>>()
    private val observableFactTitle = MutableLiveData<Fact>()
    private var shouldReturnNetworkError = false
    private val errorCode = MutableLiveData<Int>()

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insertToFactTable(fact: Fact) {
        this.fact = fact
        observableFactTitle.postValue(fact)
    }

    override suspend fun insertToFactItemsTable(factItems: List<FactItem>) {
        this.factItems.addAll(factItems)
        observableFactItems.postValue(factItems)
    }

    override fun getFactTitle(): LiveData<Fact> {
        return observableFactTitle
    }

    override fun getAllFactItems(): LiveData<List<FactItem>> {
        return observableFactItems
    }

    override suspend fun getFactsFromNetwork() {
        if (shouldReturnNetworkError) {
            errorCode.postValue(Constants.ErrorCodes.NO_INTERNET.ordinal)
        }else{
            val factTitle = Fact("About India")
            val factItems = mutableListOf<FactItem>()
            factItems.add(FactItem(
                "Beavers",
                "Beavers are second only to humans in their ability to manipulate.",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"))
            factItems.add(FactItem(
                "Flag",
                "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png"))

            insertToFactTable(factTitle)
            insertToFactItemsTable(factItems)
        }
    }

    override fun getErrorStatus(): MutableLiveData<Int> {
        return errorCode
    }
}