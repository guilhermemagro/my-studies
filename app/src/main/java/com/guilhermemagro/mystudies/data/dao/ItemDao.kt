package com.guilhermemagro.mystudies.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guilhermemagro.mystudies.data.entities.Item
import com.guilhermemagro.mystudies.data.entities.ItemWithSubItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE depth = 0")
    fun getAllParentsItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemWithSubItems(id: Int): List<ItemWithSubItems>

    @Insert
    suspend fun insert(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}
