package com.github.chickenbreeder.calculator

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Invalid arguments");
        exitProcess(-1)
    }

    val parser = Parser(args[0])
    val expr = parser.parseExpression()
    print(eval(expr))
}
