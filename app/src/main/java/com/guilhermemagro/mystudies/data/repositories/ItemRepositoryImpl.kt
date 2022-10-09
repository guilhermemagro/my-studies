package com.guilhermemagro.mystudies.data.repositories

import com.guilhermemagro.mystudies.data.dao.StudyItemDao
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.data.entities.StudyItemWithSubStudyItems
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl @Inject constructor(
    private val studyItemDao: StudyItemDao
) : ItemRepository {

    override fun getAllParentsStudyItems(): Flow<List<StudyItem>> {
        return studyItemDao.getAllParentsStudyItems()
    }

    override fun getStudyItemWithSubStudyItems(id: Int): List<StudyItemWithSubStudyItems> {
        return studyItemDao.getStudyItemWithSubStudyItems(id)
    }

    override suspend fun insert(studyItem: StudyItem) {
        studyItemDao.insert(studyItem)
    }

    override suspend fun update(studyItem: StudyItem) {
        studyItemDao.update(studyItem)
    }

    override suspend fun delete(studyItem: StudyItem) {
        studyItemDao.delete(studyItem)
    }
}
