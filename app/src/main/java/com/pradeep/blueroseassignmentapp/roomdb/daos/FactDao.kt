package com.pradeep.blueroseassignmentapp.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(factItem: FactItem)

    @Query("SELECT * FROM facts_table")
    fun getFacts(): Flow<List<FactItem>>

}