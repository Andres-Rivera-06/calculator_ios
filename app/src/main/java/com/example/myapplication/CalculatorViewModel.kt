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
                var numberStart = contentResult.size

                while (numberStart > 0) {
                    val previousItem = contentResult[numberStart - 1]
                    if (isPartOfNumber(previousItem)) {
                        numberStart--
                    } else {
                        break
                    }
                }
                val signIndex = numberStart - 1

                var itemBeforeNumber = ""
                if (signIndex >= 0) {
                    itemBeforeNumber = contentResult[signIndex]
                }

                var itemBeforeSign = ""
                if (signIndex - 1 >= 0) {
                    itemBeforeSign = contentResult[signIndex - 1]
                }

                var hasNegativeSign: Boolean
                if (itemBeforeNumber != "-") {
                    hasNegativeSign = false
                } else if (itemBeforeSign == "") {
                    hasNegativeSign = true
                } else if (isPartOfNumber(itemBeforeSign)) {
                    hasNegativeSign = false
                } else if (itemBeforeSign == "%") {
                    hasNegativeSign = false
                } else {
                    hasNegativeSign = true
                }


                if (hasNegativeSign) {
                    contentResult.removeAt(signIndex)
                } else {
                    contentResult.add(numberStart, "-")
                }

                if (contentResult.isEmpty()) {
                    result.value = "0"
                } else {
                    result.value = contentResult.joinToString("")
                }

            }

            else -> {
                if (valueButton.isEmpty()) return

                val isOperator = !isPartOfNumber(valueButton)

                var lastItem = ""
                if (contentResult.isNotEmpty()) {
                    lastItem = contentResult[contentResult.size - 1]
                }

                var lastIsOperator: Boolean
                if (lastItem == "" || lastItem == "%") {
                    lastIsOperator = false
                } else if (isPartOfNumber(lastItem)) {
                    lastIsOperator = false
                } else {
                    lastIsOperator = true
                }

                if (isOperator && lastIsOperator) {
                    contentResult[contentResult.size - 1] = valueButton
                } else {
                    contentResult.add(valueButton)
                }

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
            var lastToken = ""
            if (tokens.isNotEmpty()) {
                lastToken = tokens[tokens.size - 1]
            }

            var isNegativeSign: Boolean
            if (item == "-" && currentNumber.isEmpty() && lastToken != "%") {
                isNegativeSign = true
            } else {
                isNegativeSign = false
            }

            if (isPartOfNumber(item) || isNegativeSign) {
                currentNumber += item
            } else {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(currentNumber)
                }
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
        if (groupList.isEmpty()) {
            return "0"
        }
        if (groupList[0].toDoubleOrNull() == null) {
            groupList.add(0, "0")
        }
        return evaluate(groupList)
    }

    private fun evaluate(groupList: MutableList<String>): String {
        var result: String

        var i = 0
        while (i < groupList.size) {
            val item = groupList[i]

            var nextIsNumber = false
            if (i + 1 < groupList.size && groupList[i + 1].toDoubleOrNull() != null) {
                nextIsNumber = true
            }

            if (item == "%" && !nextIsNumber) {
                var operatorBefore = ""
                if (i - 2 >= 0) {
                    operatorBefore = groupList[i - 2]
                }

                if (operatorBefore == "+" || operatorBefore == "-") {
                    val base = evaluate(groupList.subList(0, i - 2).toMutableList())
                    groupList[i - 1] = percent(base, groupList[i - 1])
                } else {
                    groupList[i - 1] = percent("1", groupList[i - 1])
                }
                groupList.removeAt(i)
            } else {
                i++
            }
        }

        i = 0
        while (i < groupList.size) {
            val item = groupList[i]
            if (item == "x" || item == "÷" || item == "%") {
                val number1 = groupList[i - 1]
                val number2 = groupList[i + 1]
                if (item == "x") {
                    result = multiply(number1, number2)
                } else if (item == "÷") {
                    result = divide(number1, number2)
                } else {
                    result = module(number1, number2)
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

    fun module(number1: String, number2: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num2 == 0.0) {
            return "Sin definir"
        }
        if (num1 != null && num2 != null) {
            return model.module(num1, num2).toCleanString()
        } else {
            return "Error"
        }
    }

    fun percent(base: String, number: String): String {
        val num1 = base.toDoubleOrNull()
        val num2 = number.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            return model.percent(num1, num2).toCleanString()
        } else {
            return "Error"
        }
    }
}

fun Double.toCleanString(): String =
    toBigDecimal().setScale(10, java.math.RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()