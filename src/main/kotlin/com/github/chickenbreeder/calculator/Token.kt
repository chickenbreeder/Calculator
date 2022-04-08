package com.github.chickenbreeder.calculator
enum class DelimiterType {
    LPAREN,
    RPAREN
}

sealed class Token

data class NumberToken(val value: Int): Token()
data class OperatorToken(val operatorType: OperatorType): Token()
data class DelimiterToken(val type: DelimiterType): Token()
