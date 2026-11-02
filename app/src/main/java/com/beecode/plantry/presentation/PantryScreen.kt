package com.beecode.plantry.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beecode.plantry.presentation.Util.Companion.formattedQuantity
import com.beecode.plantry.presentation.Util.Companion.isNameValid
import com.beecode.plantry.presentation.Util.Companion.isQuantityValid
import com.beecode.plantry.presentation.Util.Companion.isUnitValid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryScreen(
    viewModel: PantryViewModel = viewModel()
) {
    val uiState = viewModel.pantryUiState.value
    val allFieldsValid =
        uiState.itemName.isNameValid() && uiState.quantity.isQuantityValid() && uiState.unit.isUnitValid()
    val units = listOf(
        "piece", "g", "kg", "ml", "L",
        "cup", "tbsp", "tsp", "can",
        "jar", "bottle", "package", "loaf"
    )
    var unitMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            "Pantry",
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.itemName,
                onValueChange = { viewModel.updateItemName(itemName = it) },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val isError = uiState.quantity.isNotBlank() && !uiState.quantity.isQuantityValid()
            OutlinedTextField(
                value = uiState.quantity,
                onValueChange = { viewModel.updateQuantity(quantity = it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(text = "Please enter a valid quantity")
                    } else {
                        Text(text = "Ex. 0.5, 1, 200, etc.")
                    }
                },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ExposedDropdownMenuBox(
                expanded = unitMenuExpanded,
                onExpandedChange = { unitMenuExpanded = !unitMenuExpanded }
            ) {

                OutlinedTextField(
                    value = uiState.unit,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitMenuExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    supportingText = { Text(text = "Select a unit from the list") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = unitMenuExpanded,
                    onDismissRequest = { unitMenuExpanded = false }
                ) {
                    units.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                viewModel.updateUnit(unit = option)
                                unitMenuExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }



        ElevatedButton(
            modifier = Modifier.padding(top = 16.dp),
            enabled = allFieldsValid,
            onClick = {
                viewModel.addItem()
            }
        ) {
            Text("Add")
        }

        Text(
            "Pantry Items",
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (uiState.pantryItems.isEmpty()) {
            Text("No items yet.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(
                    items = uiState.pantryItems, key = { it.id }
                ) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.name}, ${item.quantity.formattedQuantity} ${item.unit}")
                        ElevatedButton(onClick = {
                            viewModel.deleteItem(item)
                        }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}