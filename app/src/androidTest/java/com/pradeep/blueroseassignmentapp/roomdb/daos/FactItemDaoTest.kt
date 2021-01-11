package com.pradeep.blueroseassignmentapp.roomdb.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FactItemDaoTest {

    @get:Rule
    var instantTaskExecutionRule = InstantTaskExecutorRule()

    private lateinit var factsDB: FactRoomDB
    private lateinit var factItemDao: FactItemDao

    @Before
    fun setup() {
        factsDB = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FactRoomDB::class.java
        ).allowMainThreadQueries().build()

        factItemDao = factsDB.factItemDao()
    }

    @After
    fun teardown() {
        factsDB.close()
    }

    @Test
    fun insertFactItemEntity() = runBlockingTest {
        val factItem = FactItem(
            "Beavers",
            "Beavers are second only to humans in their ability to manipulate.",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        )
        factItemDao.insert(factItem)

        val factItemsFromDB = factItemDao.getFactItems().getOrAwaitValue()

        assertThat(factItemsFromDB).contains(factItem)
    }

    @Test
    fun getFactItems() = runBlockingTest {
        val factItem = FactItem(
            "Beavers",
            "Beavers are second only to humans in their ability to manipulate.",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        )
        factItemDao.insert(factItem)
        val factItem1 = FactItem(
            "Flag",
            "Warmer than you might think.",
            "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png"
        )
        factItemDao.insert(factItem1)

        val factItemsFromDB = factItemDao.getFactItems().getOrAwaitValue()

        // checking retrieved list size with actual inserted size
        assertThat(factItemsFromDB).hasSize(2)
    }

}