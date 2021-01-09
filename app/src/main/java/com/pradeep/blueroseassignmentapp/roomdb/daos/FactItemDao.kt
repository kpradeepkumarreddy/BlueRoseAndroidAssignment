package com.pradeep.blueroseassignmentapp.roomdb.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

@Dao
interface FactItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(factItem: FactItem)

    @Query("SELECT * FROM fact_items_table")
    fun getFactItems(): LiveData<List<FactItem>>

}