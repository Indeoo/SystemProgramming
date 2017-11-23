package com.venherak.lab3.syntax;

import com.venherak.lab3.syntax.alphabet.AbstractSymbol;
import com.venherak.lab3.syntax.alphabet.NonTerminal;
import com.venherak.lab3.syntax.alphabet.SymbolSequence;

public class Rule {
    private NonTerminal left;
    private SymbolSequence right;

    public Rule(NonTerminal left, SymbolSequence right) {
        this.left = left;
        this.right = right;
    }

    public Rule(NonTerminal left, AbstractSymbol right) {
        this.right = new SymbolSequence();
        this.right.add(right);
        this.left = left;
    }

    boolean checkSequenceOnRule(SymbolSequence symbolSequence) {
        return symbolSequence.toString().equals(right.toString());
    }

    public SymbolSequence getRight() {
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
