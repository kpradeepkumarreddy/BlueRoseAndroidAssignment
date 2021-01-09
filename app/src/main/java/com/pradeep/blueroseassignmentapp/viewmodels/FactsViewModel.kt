package com.pradeep.blueroseassignmentapp.viewmodels

import androidx.lifecycle.*
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import kotlinx.coroutines.launch

class FactsViewModel(private val factRepository: FactRepository) : ViewModel() {
    // Using LiveData and caching what allFacts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<FactItem>> = factRepository.allFacts.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(factItem: FactItem) = viewModelScope.launch {
        factRepository.insert(factItem)
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