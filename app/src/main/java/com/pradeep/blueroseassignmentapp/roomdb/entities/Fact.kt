package com.pradeep.blueroseassignmentapp.roomdb.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "facts_table")
data class Fact(
    @PrimaryKey(autoGenerate = true) val factId: Int,
    @SerializedName("title") val title: String,
    @Ignore
    @SerializedName("rows") val rows: List<FactItem>
)