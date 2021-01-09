package com.pradeep.blueroseassignmentapp.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fact_items_table")
data class FactItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val factItemsId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageHref") val imageHref: String
)