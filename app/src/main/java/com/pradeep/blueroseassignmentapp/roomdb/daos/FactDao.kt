package com.pradeep.blueroseassignmentapp.roomdb.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import com.pradeep.blueroseassignmentapp.roomdb.entities.relations.FactWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fact: Fact)

    @Query("SELECT * FROM facts_table")
    fun getFacts(): LiveData<Fact>

    @Transaction
    @Query("SELECT * FROM facts_table WHERE title = :title")
    fun getFactWithItems(title: String): LiveData<FactWithItems>

}