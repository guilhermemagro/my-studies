package com.guilhermemagro.mystudies.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.guilhermemagro.mystudies.data.entities.Item
import com.guilhermemagro.mystudies.data.entities.ItemWithSubItems

@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE depth = 0")
    fun getAllParentsItems(): List<Item>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemWithSubItems(id: Int): List<ItemWithSubItems>

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)
}
