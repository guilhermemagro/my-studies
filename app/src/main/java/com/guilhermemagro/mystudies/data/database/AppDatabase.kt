package com.guilhermemagro.mystudies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guilhermemagro.mystudies.data.dao.ItemDao
import com.guilhermemagro.mystudies.data.entities.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
