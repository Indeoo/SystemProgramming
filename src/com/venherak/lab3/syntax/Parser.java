package com.venherak.lab3.syntax;

import com.venherak.lab3.Exceptions.SyntaxException;
import com.venherak.lab3.Language;
import com.venherak.lab3.lexical.Token;
import com.venherak.lab3.syntax.alphabet.AbstractSymbol;
import com.venherak.lab3.syntax.alphabet.NonTerminal;
import com.venherak.lab3.syntax.alphabet.SymbolSequence;
import com.venherak.lab3.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Terminal> terminals;
    private Language language;

    public Parser(List<Token> tokens, Language language) {
        this.language = language;
        terminals = new ArrayList<>();
        for (Token token : tokens) {
            terminals.add(new Terminal(token));
        }
    }

    public void formTree() throws SyntaxException {
        SymbolSequence symbolSequence;
        for (int i = terminals.size() - 1; i >= 0; i--) {
            for (int j = i; j < terminals.size(); j++) {
                symbolSequence = new SymbolSequence();
                for (int d = terminals.size() - j; d > 0; d--) {
                    symbolSequence.add(terminals.get(i + terminals.size() - j - d));
                }
                if (transformSeqByRule(symbolSequence)) {
                    i++;
                }
             //   System.out.println(symbolSequence);
              //  System.out.println(getHighTreeLayer(symbolSequence) + " \n");
            }
        }
        for (Terminal terminal : terminals) {
            if (terminal.getParent() != null) {
                if (terminal.getRoot().getLiteral().equals("Statements")) {
                    terminal.getRoot().addParent(language.getRoot());
                    break;
                }
            }
        }
        for (Terminal terminal : terminals) {
            if (!terminal.getRoot().equals(language.getRoot())) {
                throw new SyntaxException("Syntax error near " + terminal);
            }
        }

    }

    private boolean transformSeqByRule(SymbolSequence symbolSequence) {
        symbolSequence = getHighTreeLayer(symbolSequence);
        for (Rule rule : language.getRules()) {
            if (rule.checkSequenceOnRule(symbolSequence)) {
                NonTerminal nonTerminal = new NonTerminal(rule.getLeft().getLiteral());
                for (AbstractSymbol symbol : symbolSequence) {
                    symbol.addParent(nonTerminal);
                }
                return true;
            }
        }
        return false;
    }

    SymbolSequence getRoots(SymbolSequence symbolSequence) {
        SymbolSequence serviceSequence = new SymbolSequence();
        for (AbstractSymbol symbol : symbolSequence) {
            serviceSequence.add(symbol.getRoot());
        }
        return serviceSequence;
    }

    SymbolSequence getHighTreeLayer(SymbolSequence symbolSequence) {
        symbolSequence = getRoots(symbolSequence);
        SymbolSequence serviceSequence = new SymbolSequence();
        AbstractSymbol prevSymbol = new Terminal("");
        for (AbstractSymbol symbol : symbolSequence) {
            if (!prevSymbol.getRoot().equals(symbol.getRoot())) {
                serviceSequence.add(symbol);
            }
            prevSymbol = symbol;
        }
        return serviceSequence;
    }

    public void viewTree(AbstractSymbol symbol) {
        Integer i = 0;
        viewTree(symbol, i);
    }

    public void viewTree(AbstractSymbol symbol, Integer i) {
        System.out.print(getLines(i) + symbol + "\n");
        i++;
        if (symbol.getChildren().size() > 0) {
            for (AbstractSymbol symbol1 : symbol.getChildren()) {
                viewTree(symbol1, i);
            }
        }
        //i--;
    }

    private String getLines(int i) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < i; j++) {
            result.append("  |");
        }
        result.append("");
        return result.toString();
    }

    @Override
    public String toString() {
        AbstractSymbol symbol = terminals.get(0).getRoot();
        viewTree(language.getRoot());
        return symbol + " " + symbol.getChildren();
    }
}
