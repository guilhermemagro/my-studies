package com.guilhermemagro.mystudies.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.data.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    val studyItems: LiveData<List<StudyItem>>
    get() {
        return itemRepository.getAllParentsStudyItems().asLiveData()
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

    fun deleteStudyItem(studyItem: StudyItem) {
        viewModelScope.launch {
            itemRepository.delete(studyItem)
        }
    }
}
