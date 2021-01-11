package com.pradeep.blueroseassignmentapp.roomdb.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fact: Fact)

    @Query("SELECT * FROM facts_table")
    fun getFacts(): LiveData<Fact>

}