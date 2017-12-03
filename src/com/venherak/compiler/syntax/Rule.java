package com.venherak.compiler.syntax;

import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.NonTerminal;
import com.venherak.compiler.syntax.alphabet.SymbolChain;

public class Rule {
    private NonTerminal left;
    private SymbolChain right;

    public Rule(NonTerminal left, SymbolChain right) {
        this.left = left;
        this.right = right;
    }

    public Rule(NonTerminal left, AbstractSymbol right) {
        this.right = new SymbolChain();
        this.right.add(right);
        this.left = left;
    }

    public boolean checkSequenceOnRule(SymbolChain symbolChain) {
        return symbolChain.toString().equals(right.toString());
    }

    public boolean checkBackPartCoincidence(SymbolChain symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            if (!symbols.get(symbols.size() - i - 1).getLiteral().equals(right.get(right.size() - i - 1).getLiteral())) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSequenceOnToken(AbstractSymbol symbol) {
        for (AbstractSymbol symbol1 : getRight()) {
            if (symbol.getLiteral().equals(symbol1.getLiteral())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSequenceBeginWith(AbstractSymbol symbol) {
        return getRight().get(0).getLiteral().equals(symbol.getLiteral());
    }

    public SymbolChain getRight() {
        return right;
    }

    public NonTerminal getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return left + " -> " + right;
    }

}
