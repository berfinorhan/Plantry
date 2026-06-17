package com.beecode.plantry.presentation

import com.beecode.plantry.domain.PantryItem

data class PantryUiState(
    val itemName: String = "",
    val pantryItems: List<PantryItem> = emptyList()
)