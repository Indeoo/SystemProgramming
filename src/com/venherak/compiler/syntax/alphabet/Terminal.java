package com.venherak.compiler.syntax.alphabet;

import com.venherak.compiler.lexical.Token;

public class Terminal extends AbstractSymbol {

    public Terminal(String literal) {
        super(literal);
    }

    public Terminal(Token token) {
        this.setLiteral(token.getSign());
    }
}
