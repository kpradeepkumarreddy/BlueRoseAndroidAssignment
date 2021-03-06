package com.pradeep.blueroseassignmentapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactsViewModel @ViewModelInject constructor(private val factRepository: FactRepository) :
    ViewModel() {

    // Using LiveData and caching what allFacts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val observableFactTitle: LiveData<Fact> = factRepository.getFactTitle()
    val observableFactItems: LiveData<List<FactItem>> = factRepository.getAllFactItems()
    val observableErrorStatus: LiveData<Int> = factRepository.getErrorStatus()

    fun getFactsFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            factRepository.getFactsFromNetwork()
        }
    }
}