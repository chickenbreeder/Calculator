package com.github.chickenbreeder.calculator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun complexBinary() {
        val parser = Parser("-(42 + 3 * (7 + 2))")
        val expr = parser.parseExpression()
        val expected =
            UnaryExpression(
                OperatorType.MINUS, GroupingExpression(
                    BinaryExpression(
                        NumberExpression(42), OperatorType.PLUS, BinaryExpression(
                            NumberExpression(3),
                            OperatorType.MUL,
                            GroupingExpression(
                                BinaryExpression(
                                    NumberExpression(7),
                                    OperatorType.PLUS,
                                    NumberExpression(2)
                                )
                            )
                        )
                    )
                )
            )

        Assertions.assertEquals(expected, expr)
    }
}
