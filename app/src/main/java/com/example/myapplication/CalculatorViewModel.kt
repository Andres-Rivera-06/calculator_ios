package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorModel()

    val result = mutableStateOf("0")
    var contentResult = mutableListOf("0")

    fun manageClickButtons(valueButton: String) {


        when (valueButton) {
            "AC" -> {
                contentResult.clear()
                result.value = "0"
            }

            "≪" -> {
                println(contentResult)
                contentResult.removeLastOrNull()
                println(contentResult)
                if (contentResult.isEmpty()) {
                    result.value = "0"
                } else {
                    result.value = contentResult.joinToString("")
                }
            }
            "=" -> {
                calculateResult()
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

        if(value =="."){
            return true
        }

        if (value.toDoubleOrNull() != null){
            return true
        }
        return false
    }

    fun gruopNumbers(): List<String>{
        val tokens = mutableListOf<String>()
        var currentNumber= ""

        for (item in contentResult) {
            if (isPartOfNumber(item)) {
                currentNumber += item
            } else {
                tokens.add(currentNumber)
                currentNumber = ""
                tokens.add(item)
            }
        }
        if (currentNumber.isNotEmpty()){
            tokens.add(currentNumber)
        }
        return tokens
    }

    fun calculateResult(): Double {
        val groupList = gruopNumbers()
        var result: String
        while (true){

            for (item in groupList){
                if(item == "*"){
                    val numer1 = groupList.indexOf(item) - 1
                    val number2 = groupList.indexOf(item) + 1
                    result  = multiply("numer1", "number2")
                }
            }
            break
        }


        return 0.0
    }

    fun multiply(number1: String, number2: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.multiply(num1, num2).toString()
            return result
        }else{
            return "Error"
        }
    }

    fun add(number1: String, number2: String): String {
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
    fun subtraction(number1: String, number2: String): String {
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