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
        //this.right = new BackusNaur(right, 1);
        this.right = new SymbolSequence();
        this.right.add(right);
        this.left = left;
    }

    boolean checkSequenceOnRule(SymbolSequence symbolSequence) {
       // System.out.println(right + "        " + symbolSequence);
/*        for(AbstractSymbol symbol: SymbolSequence) {
            if(symbol.)
        }*/
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
