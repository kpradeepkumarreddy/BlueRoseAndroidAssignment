package com.pradeep.blueroseassignmentapp.viewmodels

import androidx.lifecycle.*
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactsViewModel(private val factRepository: FactRepository) : ViewModel() {
    // Using LiveData and caching what allFacts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val factsFromDB: LiveData<Fact> = factRepository.factsFromDB
    val allFactItems: LiveData<List<FactItem>> = factRepository.allFactItems

    fun getFacts(){
        viewModelScope.launch(Dispatchers.IO){
            factRepository.getFacts()
        }
    }
}


class FactsViewModelFactory(private val factRepository: FactRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FactsViewModel(factRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}