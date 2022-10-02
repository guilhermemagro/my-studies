package com.guilhermemagro.mystudies.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithSubItems(
    @Embedded
    val item: Item,

    @Relation(
        parentColumn = "id",
        entityColumn = "parent_id"
    )
    val subItems: List<Item>
)
