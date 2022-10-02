package com.guilhermemagro.mystudies.data.repositories

import com.guilhermemagro.mystudies.data.entities.Item
import com.guilhermemagro.mystudies.data.entities.ItemWithSubItems
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllParentsItems(): Flow<List<Item>>
    fun getItemWithSubItems(id: Int): List<ItemWithSubItems>
    suspend fun insert(item: Item)
    suspend fun update(item: Item)
    suspend fun delete(item: Item)
}
