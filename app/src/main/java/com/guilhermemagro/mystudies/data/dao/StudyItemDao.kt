package com.guilhermemagro.mystudies.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.data.entities.StudyItemWithSubStudyItems
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyItemDao {
    @Query("SELECT * FROM study_items WHERE depth = 0")
    fun getAllParentsStudyItems(): Flow<List<StudyItem>>

    @Query("SELECT * FROM study_items WHERE id = :id")
    fun getStudyItemWithSubStudyItems(id: Int): List<StudyItemWithSubStudyItems>

    @Insert
    suspend fun insert(studyItem: StudyItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(studyItem: StudyItem)

    @Delete
    suspend fun delete(studyItem: StudyItem)
}
