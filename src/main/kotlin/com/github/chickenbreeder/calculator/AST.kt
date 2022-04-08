package com.github.chickenbreeder.calculator

enum class Associativity {
    LEFT,
    RIGHT
}

enum class OperatorType(val precedence: Int, val associativity: Associativity) {
    PLUS(10, Associativity.LEFT),
    MINUS(10, Associativity.LEFT),
    MUL(20, Associativity.LEFT),
    DIV(20, Associativity.LEFT),
    MOD(20, Associativity.LEFT)
}

sealed class Expression
data class NumberExpression(val value: Int) : Expression()
data class UnaryExpression(val operator: OperatorType, val expression: Expression) : Expression()
data class GroupingExpression(val expression: Expression) : Expression()
data class BinaryExpression(
    val lhs: Expression,
    val operator: OperatorType,
    val rhs: Expression
) : Expression()

