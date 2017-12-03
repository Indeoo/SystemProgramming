package com.venherak.compiler.languages;

import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.NonTerminal;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Language {
    private NonTerminal root;
    private List<Rule> rules;
    private List<Terminal> terminals;
    private List<NonTerminal> nonTerminals;

    public Language() {
        this.root = new NonTerminal("ROOT");
        this.rules = new ArrayList<>();
        this.terminals = new ArrayList<>();
        nonTerminals = new ArrayList<>();
        formRules();
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

    public List<Rule> findRules(AbstractSymbol symbol) {
        SymbolChain symbols = new SymbolChain();
        symbols.add(symbol);
        return findRules(symbols);
    }

    public List<Rule> findRules(SymbolChain symbolChain) {
        List<Rule> rules = new ArrayList<>();
        for (Rule rule : getRules()) {
            if (rule.checkBackPartCoincidence(symbolChain)) {
                rules.add(rule);
            }
        }
        return rules;
    }
    public List<Terminal> getTerminals() {
        return terminals;
    }

    public List<NonTerminal> getNonTerminals() {
        return nonTerminals;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRoot(NonTerminal root) {
        this.root = root;
    }

    public void viewRules() {
        System.out.println("Rules:");
        for (Rule rule : rules) {
            System.out.println(rule);
        }
    }

    public abstract void formRules();

    public abstract void getLemexeAlphabet(Set<Token> delimiterList, Set<Token> operatorList, Set<Token> keywordList);

    public abstract void formTokenRules(List<Token> tokens);

    public abstract boolean checkIfAllowedString(String string);
}
