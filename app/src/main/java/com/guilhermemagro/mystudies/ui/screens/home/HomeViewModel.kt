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
            itemRepository.insert(studyItem)
        }
    }

    fun updateStudyItem(studyItem: StudyItem) {
        viewModelScope.launch {
            itemRepository.update(studyItem)
        }
    }

    fun deleteStudyItemAndItsChildren(studyItem: StudyItem) {
        viewModelScope.launch {
            lastStudyItems?.forEach { listItem ->
                if (listItem.isChildOf(studyItem)) {
                    itemRepository.delete(listItem)
                }
            }
            itemRepository.delete(studyItem)
        }
    }
}
