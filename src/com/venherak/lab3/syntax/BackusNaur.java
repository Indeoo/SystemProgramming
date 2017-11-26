package com.venherak.lab3.syntax;

import com.venherak.lab3.syntax.alphabet.SymbolSequence;

public class BackusNaur {
    SymbolSequence symbols;
    int type;

    public BackusNaur(SymbolSequence symbols, int type) {
        this.symbols = symbols;
        this.type = type;
    }
}
