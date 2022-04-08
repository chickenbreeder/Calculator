package com.github.chickenbreeder.calculator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LexerTest {

    @Test
    fun readNumbers() {
        val lexer = Lexer("8 + (42) -190")
        val tokens = lexer.asSequence().toList()
        val expected = listOf(
            NumberToken(8),
            OperatorToken(OperatorType.PLUS),
            DelimiterToken(DelimiterType.LPAREN),
            NumberToken(42),
            DelimiterToken(DelimiterType.RPAREN),
            OperatorToken(OperatorType.MINUS),
            NumberToken(190)
        )

        Assertions.assertEquals(expected, tokens)
    }
}