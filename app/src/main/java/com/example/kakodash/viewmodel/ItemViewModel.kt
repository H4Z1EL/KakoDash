package com.example.kakodash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakodash.model.Item
import com.example.kakodash.network.NetworkModule
import com.example.kakodash.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val repo = ItemRepository(NetworkModule.provideApiService())

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items = _items.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val _editingItem = MutableStateFlow<Item?>(null)
    val editingItem = _editingItem.asStateFlow()

    fun startEdit(item: Item?) {
        _editingItem.value = item
    }
    fun saveItem(title: String, body: String) {
        val editing = editingItem.value

        viewModelScope.launch {
            _loading.value = true
            try {
                if (editing == null) {
                    repo.create(Item(title = title, body = body))
                } else {
                    repo.update(editing.id, editing.copy(title = title, body = body))
                }

                loadItems()
                _editingItem.value = null

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }


    fun loadItems() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resp = repo.getAll()
                if (resp.isSuccessful) {
                    _items.value = resp.body() ?: emptyList()
                } else {
                    _error.value = "Error ${resp.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
