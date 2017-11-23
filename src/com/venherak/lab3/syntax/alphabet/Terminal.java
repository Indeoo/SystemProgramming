package com.venherak.lab3.syntax.alphabet;

import com.venherak.lab3.lexical.Token;

public class Terminal extends AbstractSymbol {

    public Terminal(String literal) {
        super(literal);
    }

    public Terminal(Token token) {
        this.setLiteral(token.getSign());
    }
}
