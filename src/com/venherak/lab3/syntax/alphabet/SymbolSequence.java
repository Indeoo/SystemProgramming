package com.venherak.lab3.syntax.alphabet;

import java.util.ArrayList;

public class SymbolSequence extends ArrayList<AbstractSymbol> {
    int status = 0;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (AbstractSymbol symbol : this) {
            result.append(symbol);
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
