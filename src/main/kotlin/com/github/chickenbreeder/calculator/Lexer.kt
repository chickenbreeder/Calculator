package com.github.chickenbreeder.calculator

class Lexer(source: String) : Sequence<Token> {
    private val iterator: TokenIterator = TokenIterator(source)

    override fun iterator(): TokenIterator {
        return iterator
    }
}

class TokenIterator(
    private val source: String,
    private val sourceLength: Int = source.length,
    private var offset: Int = 0
) : Iterator<Token> {
    override fun next(): Token {
        return readToken() ?: throw NoSuchElementException()
    }

    override fun hasNext(): Boolean {
        return offset < sourceLength
    }

    fun peekToken(): Token? {
        val oldOffset = offset
        val token = this.readToken() ?: return null
        offset = oldOffset
        return token
    }

    private fun readToken(): Token? {
        val (c, off) = readChar() ?: return null

        when (c) {
            '+' -> return OperatorToken(OperatorType.PLUS)
            '-' -> return OperatorToken(OperatorType.MINUS)
            '*' -> return OperatorToken(OperatorType.MUL)
            '(' -> return DelimiterToken(DelimiterType.LPAREN)
            ')' -> return DelimiterToken(DelimiterType.RPAREN)
            else -> {
                if (c.isWhitespace()) {
                    return readToken()
                }
                if (c.isDigit()) {
                    return readNumber(off)
                }

                throw RuntimeException("Invalid char '$c'")
            }
        }
    }

    private fun readNumber(from: Int): Token {
        var result = peekChar()

        while (result != null) {
            val c = result.first
            val off = result.second

            if (!c.isDigit()) {
                return NumberToken(source.slice(from until off).toInt())
            }
            bump()
            result = peekChar()
        }
        return NumberToken(source.slice(from until sourceLength).toInt())
    }

    private fun readChar(): Pair<Char, Int>? {
        if (hasNext()) {
            val c = source[offset]
            return Pair(c, offset++)
        }
        return null
    }

    private fun bump() {
        readChar()
    }

    private fun peekChar(): Pair<Char, Int>? {
        if (hasNext()) {
            return Pair(source[offset], offset);
        }
        return null
    }
}
