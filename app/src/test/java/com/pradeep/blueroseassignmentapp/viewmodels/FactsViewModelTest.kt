package com.pradeep.blueroseassignmentapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import com.pradeep.blueroseassignmentapp.repositories.FakeFactRepositoryImpl
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.utils.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FactsViewModelTest {

    @get:Rule
    var instantTaskExecutionRule = InstantTaskExecutorRule()

    private lateinit var factsViewModel: FactsViewModel
    private lateinit var fakeFactRepositoryImpl: FakeFactRepositoryImpl

    @Before
    fun setup() {
        fakeFactRepositoryImpl = FakeFactRepositoryImpl()
        factsViewModel = FactsViewModel(fakeFactRepositoryImpl)
    }

    @Test
    fun `get facts from network success case`() {
        factsViewModel.getFactsFromNetwork()
        val factTitle = factsViewModel.observableFactTitle.getOrAwaitValueTest()
        val factItems = factsViewModel.observableFactItems.getOrAwaitValueTest()
        assertThat(factTitle).isEqualTo(Fact("About India"))
        assertThat(factItems).hasSize(2)
    }

    @Test
    fun `get facts from network failure case`() {
        fakeFactRepositoryImpl.setShouldReturnNetworkError(true)
        factsViewModel.getFactsFromNetwork()
        val errorStatus = factsViewModel.observableErrorStatus.getOrAwaitValueTest()

        assertThat(errorStatus).isEqualTo(Constants.ErrorCodes.NO_INTERNET.ordinal)
    }
}