package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val model = CalculatorModel()

    val result = mutableStateOf("0")
    var contentResult = mutableListOf("0")

    fun manageClickButtons(valueButton: String){
        if (valueButton.isEmpty()) return
        contentResult.add(valueButton)

        result.value = contentResult.joinToString("")

        when (valueButton) {
            "-" -> {

            }

        }

    }

    fun calculateResult(): Double{
        var result: Double = 0.0
        return result
    }

    fun add(number1: String, number2: String): String{
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.add(num1, num2).toString()
            return result
        } else {
            return "Error"
        }
    }

    @Composable
    fun subtraction(number1: String, number2: String): String{
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.subtraction(num1, num2).toString()
            return result
        } else {
            return "Error"
        }
    }

}