package com.guilhermemagro.mystudies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guilhermemagro.mystudies.data.dao.StudyItemDao
import com.guilhermemagro.mystudies.data.entities.StudyItem

@Database(entities = [StudyItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studyItemDao(): StudyItemDao
}
