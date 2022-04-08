package com.github.chickenbreeder.calculator

import com.github.chickenbreeder.calculator.Parser
import com.github.chickenbreeder.calculator.eval
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EvalTest {

    @Test
    fun unary() {
        val parser = Parser("-42")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        Assertions.assertEquals(-42, eval(expr))
    }

    @Test
    fun simpleBinary() {
        val parser = Parser("42 + 3 * 7")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        Assertions.assertEquals(63, eval(expr))
    }

    @Test
    fun simpleBinaryWithGrouping() {
        val parser = Parser("(42 + 3) * 7")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        Assertions.assertEquals(315, eval(expr))
    }

    @Test
    fun simpleBinaryWithGrouping_2() {
        val parser = Parser("(42 + 3) * (7 * 18)")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        Assertions.assertEquals(5670, eval(expr))
    }
    // -(42 + 3 * (7 + 2))

    @Test
    fun complexBinary() {
        val parser = Parser("-42 + 3 * (7 + 2)")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        Assertions.assertEquals(-15, eval(expr))
    }

    @Test
    fun complexBinary_2() {
        val parser = Parser("--(70 + 3 * (7 + 2))")
        val expr = parser.parseExpression() ?: throw RuntimeException("no expression found")

        Assertions.assertEquals(97, eval(expr))
    }
}
