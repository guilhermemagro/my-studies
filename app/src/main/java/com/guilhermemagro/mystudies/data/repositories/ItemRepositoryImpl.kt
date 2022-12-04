package com.guilhermemagro.mystudies.data.repositories

import com.guilhermemagro.mystudies.data.dao.StudyItemDao
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.data.entities.StudyItemWithSubStudyItems
import com.guilhermemagro.mystudies.utils.ROOT_PARENT_ID
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemRepositoryImpl @Inject constructor(
    private val studyItemDao: StudyItemDao
) : ItemRepository {

    override fun getAllParentsStudyItems(): Flow<List<StudyItem>> {
        return studyItemDao.getAllParentsStudyItems().orderStudyItemsByParentId()
    }

    private fun Flow<List<StudyItem>>.orderStudyItemsByParentId(): Flow<List<StudyItem>> {
        return map { list ->
            val itemsByParentId = list.groupBy { it.parentId }
            val resultItemsList = mutableListOf<StudyItem>()
            if (itemsByParentId.containsKey(ROOT_PARENT_ID)) {
                itemsByParentId[ROOT_PARENT_ID]?.let { rootItems ->
                    val rootItemsSorted = rootItems.sortedBy { it.position }
                    resultItemsList.addAll(rootItemsSorted)
                }
                itemsByParentId.keys.forEach { parentId ->
                    if (parentId != ROOT_PARENT_ID) {
                        itemsByParentId[parentId]?.let { items ->
                            val parentIndex = resultItemsList.indexOfFirst { it.id == parentId }
                            resultItemsList.addAll(parentIndex.inc(), items)
                        }
                    }
                }
            }
            resultItemsList
        }
    }

    override fun getStudyItemWithSubStudyItems(): Flow<List<StudyItemWithSubStudyItems>> {
        return studyItemDao.getStudyItemWithSubStudyItems()
    }

    override suspend fun insert(studyItem: StudyItem) {
        studyItemDao.insert(studyItem)
    }

    override suspend fun update(vararg studyItem: StudyItem) {
        studyItemDao.update(*studyItem)
    }

    override suspend fun delete(studyItem: StudyItem) {
        studyItemDao.delete(studyItem)
    }

    override fun getMaxPosition(): Flow<Int?> {
        return studyItemDao.getMaxPosition()
    }

    override suspend fun deleteAndUpdateAll(
        studyItemToBeDeleted: StudyItem,
        studyItemsToBeUpdated: List<StudyItem>
    ) {
        studyItemDao.deleteAndUpdateAll(
            studyItemToBeDeleted = studyItemToBeDeleted,
            studyItemsToBeUpdated = studyItemsToBeUpdated
        )
    }
}
