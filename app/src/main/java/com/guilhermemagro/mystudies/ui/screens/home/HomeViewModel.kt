package com.guilhermemagro.mystudies.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.data.entities.StudyItemWithSubStudyItems
import com.guilhermemagro.mystudies.data.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val studyItemsFlow: Flow<List<StudyItem>> = itemRepository.getAllParentsStudyItems()
    val studyItems: LiveData<List<StudyItem>> = studyItemsFlow.asLiveData()
    private var lastStudyItems: List<StudyItem>? = null

    private val maxPositionFlow: Flow<Int?> = itemRepository.getMaxPosition()
    val maxPosition: LiveData<Int?> = maxPositionFlow.asLiveData()

    val studyItemsTest: LiveData<List<StudyItemWithSubStudyItems>>
    get() {
        return itemRepository.getStudyItemWithSubStudyItems().asLiveData()
    }

    init {
        viewModelScope.launch {
            studyItemsFlow.collect { lastStudyItems = it }
        }
    }

    fun addStudyItem(studyItem: StudyItem) {
        viewModelScope.launch {
            if (studyItem.depth == 0) {
                studyItem.position = maxPosition.value?.inc() ?: 0
            }
            itemRepository.insert(studyItem)
        }
    }

    fun updateStudyItem(studyItem: StudyItem) {
        viewModelScope.launch {
            itemRepository.update(studyItem)
        }
    }

    fun moveItemUp(studyItem: StudyItem) = viewModelScope.launch {
        lastStudyItems?.let { studyItems ->
            val studyItemMovedDown = studyItems.find { it.position == studyItem.position.dec() }
            studyItemMovedDown?.let {
                moveItemsAndUpdate(studyItemMovedDown, studyItem)
            }
        }
    }

    fun moveItemDown(studyItem: StudyItem) = viewModelScope.launch {
        lastStudyItems?.let { studyItems ->
            val studyItemMovedUp = studyItems.find { it.position == studyItem.position.inc() }
            studyItemMovedUp?.let {
                moveItemsAndUpdate(studyItem, studyItemMovedUp)
            }
        }
    }

    private suspend fun moveItemsAndUpdate(
        studyItemMovedDown: StudyItem,
        studyItemMovedUp: StudyItem
    ) {
        studyItemMovedDown.position++
        studyItemMovedUp.position--
        itemRepository.update(studyItemMovedDown, studyItemMovedUp)
    }

    fun deleteStudyItemAndItsChildren(studyItem: StudyItem) {
        viewModelScope.launch {
            lastStudyItems?.forEach { listItem ->
                if (listItem.isChildOf(studyItem)) {
                    itemRepository.delete(listItem)
                }
            }
            lastStudyItems?.let { studyItems ->
                val itemsToBeUpdated = studyItems.toMutableList()
                itemsToBeUpdated.filter { it.position > studyItem.position }.map { it.position-- }
                itemRepository.deleteAndUpdateAll(
                    studyItemToBeDeleted = studyItem,
                    studyItemsToBeUpdated = itemsToBeUpdated
                )
            }
            itemRepository.delete(studyItem)
        }
    }
}
