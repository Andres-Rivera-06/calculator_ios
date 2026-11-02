package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorModel()

    val result = mutableStateOf("0")
    var contentResult = mutableListOf<String>()
    val previousOperation = mutableStateOf("")

    fun manageClickButtons(valueButton: String) {


        when (valueButton) {
            "AC" -> {
                contentResult.clear()
                result.value = "0"
                previousOperation.value = " "
            }

            "≪" -> {
                contentResult.removeLastOrNull()
                previousOperation.value = ""
                if (contentResult.isEmpty()) {
                    result.value = "0"
                } else {
                    result.value = contentResult.joinToString("")
                }
            }

            "=" -> {
                val resultOperation = calculateResult()
                result.value = resultOperation
                previousOperation.value = contentResult.joinToString("")
                contentResult.clear()
                if (resultOperation.toDoubleOrNull() != null) {
                    contentResult.addAll(resultOperation.map { it.toString() })
                }

            }

            "⁺/₋" -> {

            }

            else -> {
                if (valueButton.isEmpty()) return
                contentResult.add(valueButton)

                result.value = contentResult.joinToString("")
            }


        }

    }

    private fun isPartOfNumber(value: String): Boolean {

        if (value == ".") {
            return true
        }

        if (value.toDoubleOrNull() != null) {
            return true
        }
        return false
    }

    fun groupNumbers(): MutableList<String> {
        val tokens = mutableListOf<String>()
        var currentNumber = ""

        for (item in contentResult) {
            var isNegativeSign: Boolean

            if (item == "-" && currentNumber.isEmpty()) {
                isNegativeSign = true
            } else {
                isNegativeSign = false
            }

            if (isPartOfNumber(item) || isNegativeSign) {
                currentNumber += item
            } else {
                tokens.add(currentNumber)
                currentNumber = ""
                tokens.add(item)
            }
        }
        if (currentNumber.isNotEmpty()) {
            tokens.add(currentNumber)
        }
        return tokens
    }

    fun calculateResult(): String {
        val groupList = groupNumbers()
        var result: String

        var i = 0
        while (i < groupList.size) {
            val item = groupList[i]
            if (item == "x" || item == "÷") {
                val number1 = groupList[i - 1]
                val number2 = groupList[i + 1]
                if (item == "x") {
                    result = multiply(number1, number2)
                } else {
                    result = divide(number1, number2)
                }

                groupList[i - 1] = result
                groupList.removeAt(i + 1)
                groupList.removeAt(i)

            } else {
                i++
            }
        }

        i = 0
        while (i < groupList.size) {
            val item = groupList[i]
            if (item == "+" || item == "-") {
                val number1 = groupList[i - 1]
                val number2 = groupList[i + 1]
                if (item == "+") {
                    result = add(number1, number2)
                } else {
                    result = subtraction(number1, number2)
                }

                groupList[i - 1] = result
                groupList.removeAt(i + 1)
                groupList.removeAt(i)
            } else {
                i++
            }
        }


        return groupList[0]
    }

    fun multiply(number1: String, number2: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.multiply(num1, num2).toCleanString()
            return result
        } else {
            return "Error"
        }
    }

    fun divide(number1: String, number2: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num2 == 0.0) {
            return "Sin definir"
        }
        if (num1 != null && num2 != null) {
            val result = model.divide(num1, num2).toCleanString()
            return result
        } else {
            return "Error"
        }
    }

    fun add(number1: String, number2: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.add(num1, num2).toCleanString()
            return result
        } else {
            return "Error"
        }
    }

    fun subtraction(number1: String, number2: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.subtraction(num1, num2).toCleanString()
            return result
        } else {
            return "Error"
        }
    }

}

fun Double.toCleanString(): String =
    toBigDecimal()
        .setScale(10, java.math.RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString()