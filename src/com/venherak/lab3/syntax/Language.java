package com.venherak.lab3.syntax;

import com.venherak.lab3.syntax.alphabet.AbstractSymbol;
import com.venherak.lab3.syntax.alphabet.NonTerminal;
import com.venherak.lab3.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;

public class Language {
    private NonTerminal root;
    private List<Rule> rules;
    private List<Terminal> terminals;
    private List<NonTerminal> nonTerminals;

    public Language(List<Rule> rules, List<Terminal> terminals, List<NonTerminal> nonTerminals, NonTerminal root) {
        this.root = root;
        this.rules = rules;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
    }

    boolean checkSymbol(AbstractSymbol symbol) {
        List<AbstractSymbol> alphabet = getAllAlphabet();
        for (AbstractSymbol symbol1 : alphabet) {
            if (symbol.getLiteral().equals(symbol1.getLiteral())) {
                return true;
            }
        }
        return false;
    }

    public AbstractSymbol getRoot() {
        return root;
    }

    List<AbstractSymbol> getAllAlphabet() {
        List<AbstractSymbol> abstractSymbolList = new ArrayList<>();
        abstractSymbolList.addAll(terminals);
        abstractSymbolList.addAll(nonTerminals);
        return abstractSymbolList;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRoot(NonTerminal root) {
        this.root = root;
    }

    public void viewRuels() {
        System.out.println("Rules:");
        for (Rule rule : rules) {
            System.out.println(rule);
        }
    }
}
