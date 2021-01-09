package com.pradeep.blueroseassignmentapp.models

import com.google.gson.annotations.SerializedName
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

data class Facts(
    @SerializedName("title") val title: String,
    @SerializedName("rows") val rows: List<FactItem>,
)