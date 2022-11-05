package com.guilhermemagro.mystudies.data.repositories

import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.data.entities.StudyItemWithSubStudyItems
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllParentsStudyItems(): Flow<List<StudyItem>>
    fun getStudyItemWithSubStudyItems(): Flow<List<StudyItemWithSubStudyItems>>
    suspend fun insert(studyItem: StudyItem)
    suspend fun update(studyItem: StudyItem)
    suspend fun delete(studyItem: StudyItem)
}
