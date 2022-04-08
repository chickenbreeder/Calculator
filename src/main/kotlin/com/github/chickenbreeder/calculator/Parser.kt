package com.github.chickenbreeder.calculator

class Parser(source: String) {
    private val tokenIterator = Lexer(source).iterator()

    fun parseExpression(minPrecedence: Int = 1): Expression {
        var lhs = parseExpressionLHS() ?: throw RuntimeException("Expected expression, found EOF")
        var result = tokenIterator.peekToken()

        while (result is OperatorToken && result.operatorType.precedence >= minPrecedence) {
            bump()

            val nextMinPrecedence =
                if (result.operatorType.associativity == Associativity.LEFT)
                    result.operatorType.precedence + 1
                else
                    result.operatorType.precedence

            val rhs = parseExpression(nextMinPrecedence)

            lhs = BinaryExpression(lhs, result.operatorType, rhs)
            result = tokenIterator.peekToken()
        }
        return lhs
    }

    private fun parseExpressionLHS(): Expression? {
        if (!tokenIterator.hasNext()) {
            return null
        }

        return when (val token = tokenIterator.next()) {
            is NumberToken -> NumberExpression(token.value)
            is OperatorToken -> {
                val operator = token.operatorType
                val expression = parseExpressionLHS() ?: throw RuntimeException("Expected expression")
                UnaryExpression(operator, expression)
            }
            is DelimiterToken -> when(token.type) {
                DelimiterType.LPAREN -> {
                    val expression = parseExpression()
                    eatDelimiter(DelimiterType.RPAREN)
                    return GroupingExpression(expression)
                }
                else -> throw RuntimeException("Expected expression, found $token")
            }
            else -> throw RuntimeException("Expected expression, found `$token`")
        }
    }

    private fun bump() {
        tokenIterator.next()
    }

    private fun eatDelimiter(expected: DelimiterType) {
        if (!tokenIterator.hasNext()) {
            throw RuntimeException("Expected $expected, found EOF")
        }
        when (val token = tokenIterator.next()) {
            is DelimiterToken -> {
                if (token.type != expected) {
                    throw RuntimeException("Expected $expected, found ${token.type}")
                }
            }
            else -> throw RuntimeException("Expected $expected, got something else")
        }
    }
}
