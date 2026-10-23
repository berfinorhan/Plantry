package com.beecode.plantry.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun PantryScreen(
    viewModel: PantryViewModel = viewModel()
) {
    val uiState = viewModel.pantryUiState.value
    val allFieldsValid = uiState.itemName.isNameValid() && uiState.quantity.isQuantityValid() && uiState.unit.isUnitValid()

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
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = uiState.quantity,
                onValueChange = { viewModel.updateQuantity(quantity = it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                isError = !uiState.quantity.isQuantityValid(),
                label = { Text("Quantity") },
            )

            //todo: dropdown
            OutlinedTextField(
                value = uiState.unit,
                onValueChange = { viewModel.updateUnit(unit = it) },
                label = { Text("Unit") },
            )
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
            for (item in uiState.pantryItems) {
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