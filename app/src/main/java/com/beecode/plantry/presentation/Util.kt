package com.beecode.plantry.presentation

class Util {
    companion object {
        fun String.isQuantityValid(): Boolean {
            val doubleVal = this.toDoubleOrNull()
            return this.isNotBlank() && doubleVal != null && doubleVal > 0.0
        }

        fun String.isNameValid(): Boolean {
            return this.isNotBlank()
        }

        fun String.isUnitValid(): Boolean { //todo: need to switch to enum
            return this.isNotBlank()
        }

        val Double.formattedQuantity: String
            get() = if (this % 1.0 == 0.0) {
                this.toLong().toString()
            } else {
                this.toString()
            }
    }
}