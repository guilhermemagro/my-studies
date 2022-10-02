package com.guilhermemagro.mystudies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.guilhermemagro.mystudies.data.entities.Item
import com.guilhermemagro.mystudies.data.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    val items: LiveData<List<Item>> = itemRepository.getAllParentsItems().asLiveData()
}
