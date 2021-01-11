package com.pradeep.blueroseassignmentapp.roomdb.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Subject
import com.google.common.truth.Truth.assertThat
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FactDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutionRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var factsDB: FactRoomDB
    private lateinit var factDao: FactDao

    @Before
    fun setup() {
        hiltRule.inject()
        factDao = factsDB.factDao()
    }

    @After
    fun teardown() {
        factsDB.close()
    }

    @Test
    fun insertFactEntity() = runBlockingTest {
        val fact = Fact("About India")
        factDao.insert(fact)

        val factFromDB = factDao.getFacts().getOrAwaitValue()

        assertThat(factFromDB).isEqualTo(fact)
    }

}