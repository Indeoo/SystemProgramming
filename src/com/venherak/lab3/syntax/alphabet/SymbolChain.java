package com.venherak.lab3.syntax.alphabet;

import com.venherak.lab3.syntax.Rule;

import java.util.ArrayList;
import java.util.List;

public class SymbolChain extends ArrayList<AbstractSymbol> {

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (AbstractSymbol symbol : this) {
            result.append(symbol);
        }
        return result.toString();
    }

    public SymbolChain getHighTreeLayer() {
        SymbolChain symbolChain = this.getRoots();
        SymbolChain serviceSequence = new SymbolChain();
        AbstractSymbol prevSymbol = new Terminal("");
        for (AbstractSymbol symbol : symbolChain) {
            if (!prevSymbol.getRoot().equals(symbol.getRoot())) {
                serviceSequence.add(symbol);
            }
            prevSymbol = symbol;
        }
        return serviceSequence;
    }

    SymbolChain getRoots() {
        SymbolChain serviceSequence = new SymbolChain();
        for (AbstractSymbol symbol : this) {
            serviceSequence.add(symbol.getRoot());
        }
        return serviceSequence;
    }

    public boolean reduceChainByRule(List<Rule> ruleList) {
        SymbolChain symbolChain = this.getHighTreeLayer();
        for (Rule rule : ruleList) {
            if (rule.checkSequenceOnRule(symbolChain)) {
                NonTerminal nonTerminal = new NonTerminal(rule.getLeft().getLiteral());
                for (AbstractSymbol symbol : symbolChain) {
                    symbol.addParent(nonTerminal);
                }
                return true;
            }
        }
        return false;
    }

    public void addParent(NonTerminal nonTerminal) {
        for(AbstractSymbol symbol: this) {
            symbol.addParent(nonTerminal);
        }
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
