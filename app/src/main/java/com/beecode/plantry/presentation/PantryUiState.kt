package com.beecode.plantry.presentation

import com.beecode.plantry.domain.PantryItem

data class PantryUiState(
    val itemName: String,
    val quantity: String,
    val unit: String, //todo: should be an enum
    val pantryItems: List<PantryItem> = emptyList()
)