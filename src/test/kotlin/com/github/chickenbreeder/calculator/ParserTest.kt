package com.github.chickenbreeder.calculator

import com.github.chickenbreeder.calculator.Parser
import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun complexBinary() {
        val parser = Parser("-(42 + 3 * (7 + 2))")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        println(expr)
    }
}