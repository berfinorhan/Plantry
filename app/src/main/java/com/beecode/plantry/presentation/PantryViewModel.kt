package com.beecode.plantry.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.beecode.plantry.domain.PantryItem
import androidx.compose.runtime.State
import java.util.UUID

class PantryViewModel: ViewModel() {

    private val _pantryUiState = mutableStateOf(PantryUiState(
        itemName = "",
        quantity = "",
        unit = "",
    ))
    val pantryUiState: State<PantryUiState> = _pantryUiState

    fun addItem() {
        val trimmedName = pantryUiState.value.itemName.trim()
        if (trimmedName.isNotBlank()) {
            val newItem = PantryItem(
                id = UUID.randomUUID(),
                name = trimmedName,
                quantity = pantryUiState.value.quantity.toDoubleOrNull() ?: 1.0,
                unit = pantryUiState.value.unit
            )
            _pantryUiState.value = _pantryUiState.value.copy(pantryItems = pantryUiState.value.pantryItems + newItem)
            _pantryUiState.value = _pantryUiState.value.copy(itemName = "", quantity = "", unit = "")
        }
    }

    fun updateItemName(itemName: String) {
        _pantryUiState.value = _pantryUiState.value.copy(itemName = itemName)
    }

    fun updateQuantity(quantity: String) {
        _pantryUiState.value = _pantryUiState.value.copy(quantity = quantity)
    }

    fun updateUnit(unit: String) {
        _pantryUiState.value = _pantryUiState.value.copy(unit = unit)
    }

    fun deleteItem(item: PantryItem) {
        _pantryUiState.value = _pantryUiState.value.copy(pantryItems = pantryUiState.value.pantryItems.filter { it.id != item.id })
    }
}