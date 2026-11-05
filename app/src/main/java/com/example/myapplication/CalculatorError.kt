package com.example.myapplication

sealed class CalculatorError {
    data object DivisionByZero : CalculatorError()
    data object InvalidOperation : CalculatorError()
}

class CalculatorException(val error: CalculatorError) : Exception()
