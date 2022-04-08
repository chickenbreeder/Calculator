package com.github.chickenbreeder.calculator

fun eval(expression: Expression): Int {
    return when (expression) {
        is NumberExpression -> expression.value
        is UnaryExpression -> return when (expression.operator) {
            OperatorType.PLUS -> eval(expression.expression)
            OperatorType.MINUS -> -eval(expression.expression)
            else -> throw RuntimeException("Expected unary operator, found $expression")
        }
        is GroupingExpression -> return eval(expression.expression)
        is BinaryExpression -> return when (expression.operator) {
            OperatorType.PLUS -> eval(expression.lhs) + eval(expression.rhs)
            OperatorType.MINUS -> eval(expression.lhs) - eval(expression.rhs)
            OperatorType.MUL -> eval(expression.lhs) * eval(expression.rhs)
            OperatorType.DIV -> eval(expression.lhs) / eval(expression.rhs)
            OperatorType.MOD -> eval(expression.lhs) % eval(expression.rhs)
        }
    }
}
