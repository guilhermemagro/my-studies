package com.guilhermemagro.mystudies.data.repositories

import com.guilhermemagro.mystudies.data.dao.ItemDao
import com.guilhermemagro.mystudies.data.entities.Item
import com.guilhermemagro.mystudies.data.entities.ItemWithSubItems
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao
) : ItemRepository {

    override fun getAllParentsItems(): Flow<List<Item>> {
        return itemDao.getAllParentsItems()
    }

    override fun getItemWithSubItems(id: Int): List<ItemWithSubItems> {
        return itemDao.getItemWithSubItems(id)
    }

    override suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    override suspend fun update(item: Item) {
        itemDao.update(item)
    }

    override suspend fun delete(item: Item) {
        itemDao.delete(item)
    }
}
