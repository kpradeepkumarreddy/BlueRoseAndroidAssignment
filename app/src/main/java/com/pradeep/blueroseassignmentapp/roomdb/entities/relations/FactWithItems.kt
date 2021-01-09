package com.pradeep.blueroseassignmentapp.roomdb.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

data class FactWithItems(
    @Embedded val fact: Fact,
    @Relation(
        parentColumn = "title",
        entityColumn = "factTitle"
    )
    val factItems: List<FactItem>
)