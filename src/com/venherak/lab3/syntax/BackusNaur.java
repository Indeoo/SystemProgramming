package com.venherak.lab3.syntax;

import com.venherak.lab3.syntax.alphabet.SymbolChain;

public class BackusNaur {
    SymbolChain symbols;
    int type;

    public BackusNaur(SymbolChain symbols, int type) {
        this.symbols = symbols;
        this.type = type;
    }
}
