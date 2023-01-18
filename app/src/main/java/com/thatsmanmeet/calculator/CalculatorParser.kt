package com.thatsmanmeet.calculator

import java.text.DecimalFormat

class CalculatorParser(private var expression: String) {
    private var isNegative = false

    init {
        if (expression.startsWith("-")) {
            expression = expression.substring(1)
            val splitter = expression.split("+", "-", "*", "/", "^")
            expression = if (expression.contains("+")) {
                "${splitter[1]}-${splitter[0]}"
            } else if (expression.contains("-")) {
                "${splitter[0]}+${splitter[1]}"
            } else if (expression.contains("*")) {
                "${splitter[0]}*${splitter[1]}"
            } else if (expression.contains("/")) {
                "${splitter[0]}/${splitter[1]}"
            } else {
                "${splitter[0]}^${splitter[1]}"
            }
            isNegative = true
            println(expression)
        } else if (expression.isEmpty() || expression == "0") {
            expression = "0+0"
        } else if (expression.split("+", "-", "*", "/", "^").size == 1) {
            expression = "$expression+0"
        }
    }

    private val splitStr = expression.split("+", "-", "*", "/", "^").toMutableList()
    private val a = splitStr[0].toDouble()
    private val b = splitStr[1].toDouble()
    private val operator = getDelimiter(expression)

    private fun getDelimiter(str: String): String {
        return if (str.contains("+")) {
            "+"
        } else if (str.contains("-")) {
            "-"
        } else if (str.contains("*")) {
            "*"
        } else if (str.contains("^")) {
            "^"
        } else {
            "/"
        }
    }

    private fun calculate(): String {
        return when (operator) {
            "+" -> "${a + b}"
            "-" -> "${a - b}"
            "*" -> "${a * b}"
            "/" -> {
                if (b.toInt() != 0) "${a / b}" else throw Exception("You cannot divide a number by Zero.")
            }
            "^" -> pow(a.toInt(), b.toInt()).toDouble().toString()
            else -> {
                "0.0"
            }
        }
    }

    fun eval(calculatedData: String = calculate()): String {
        var result = calculatedData
        var final = ""


        if(result.contains("E")){
            result = DecimalFormat("0").format(calculatedData.toBigDecimal()).toString()
            final = result
        }

        if (result.contains(".")) {
            val splitResult = result.split(".")
            final = if (splitResult[1].toLong() > 0) {
                result
            } else {
                result.replace(".","").dropLast(1)
            }
        }
        return if (isNegative && final.toInt() != 0) {
            if (final.contains("-")) final.replace("--", "-") else "-$final"
        } else {
            final
        }
    }

    private fun pow(base: Int, power: Int): Int {
        return if(power == 0){
            1
        }else{
            var result = base
            var i = 1
            while (i < power) {
                result *= base
                i++
            }
            result
        }
    }
}
