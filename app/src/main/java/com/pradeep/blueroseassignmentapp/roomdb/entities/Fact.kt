package com.pradeep.blueroseassignmentapp.roomdb.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "facts_table")
data class Fact(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("title") val title: String,
)
