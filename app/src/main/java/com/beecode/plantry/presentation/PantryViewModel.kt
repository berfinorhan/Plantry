package com.beecode.plantry.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.beecode.plantry.domain.PantryItem
import androidx.compose.runtime.State
import java.util.UUID

class PantryViewModel: ViewModel() {

    private val _pantryUiState = mutableStateOf(PantryUiState())
    val pantryUiState: State<PantryUiState> = _pantryUiState

    fun addItem() {
        val trimmedName = pantryUiState.value.itemName.trim()
        if (trimmedName.isNotEmpty()) {
            val newItem = PantryItem(UUID.randomUUID(), trimmedName)
            _pantryUiState.value = _pantryUiState.value.copy(pantryItems = pantryUiState.value.pantryItems + newItem)
        }
    }

    fun updateItemName(itemName: String) {
        _pantryUiState.value = _pantryUiState.value.copy(itemName = itemName)
    }

    fun deleteItem(item: PantryItem) {
        _pantryUiState.value = _pantryUiState.value.copy(pantryItems = pantryUiState.value.pantryItems.filter { it.id != item.id })
    }
}