package com.venherak.lab3.syntax;

import com.venherak.lab3.exceptions.SyntaxException;
import com.venherak.lab3.languages.Language;
import com.venherak.lab3.lexical.Token;
import com.venherak.lab3.syntax.alphabet.AbstractSymbol;
import com.venherak.lab3.syntax.alphabet.SymbolChain;
import com.venherak.lab3.syntax.alphabet.Terminal;

import java.util.List;

public class Parser {
    private SymbolChain terminals;
    private Language language;

    public Parser(List<Token> tokens, Language language) {
        this.language = language;
        terminals = new SymbolChain();
        for (Token token : tokens) {
            terminals.add(new Terminal(token));
        }
    }

    public void formTree() throws SyntaxException {
        SymbolChain symbolChain;
        for (int i = terminals.size() - 1; i >= 0; i--) {
            for (int j = i; j < terminals.size(); j++) {
                symbolChain = new SymbolChain();
                for (int d = terminals.size() - j; d > 0; d--) {
                    symbolChain.add(terminals.get(i + terminals.size() - j - d));
                }
                if (symbolChain.reduceChainByRule(language.getRules())) {
                    i++;
                }
                //System.out.println(symbolChain);
                //System.out.println(symbolChain.getHighTreeLayer() + " \n");
            }
        }
        System.out.println(terminals.getHighTreeLayer());
        System.out.println();
        checkOnErrors();
    }

    private void checkOnErrors() throws SyntaxException {
        SymbolChain symbols = terminals.getHighTreeLayer();
        StringBuilder errorMsg = new StringBuilder();
        SymbolChain errorSequence = new SymbolChain();
        errorSequence.add(0, symbols.get(symbols.size() - 1));

        if (symbols.size() > 1) {
            for (int i = symbols.size() - 1; i >= 0; i--) {
                System.out.println(language.findRules(errorSequence));
                if (language.findRules(errorSequence).size() == 0) {
                    errorMsg.append(errorSequence.get(errorSequence.size() - 1)).append(" - EXTRA SYMBOL\n");
                    errorSequence = new SymbolChain();
                } else {
                    errorSequence.add(0, symbols.get(i));
                    System.out.println(errorSequence + "ASD");
                }
            }
            throw new SyntaxException("Syntax exception\n" + errorMsg);
        }
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
    }

    private String getLines(int i) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < i; j++) {
            result.append("  |");
        }
        result.append("");
        return result.toString();
    }

    public SymbolChain getTerminals() {
        return terminals;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        AbstractSymbol symbol = terminals.get(0).getRoot();
        viewTree(language.getRoot());
        return symbol + " " + symbol.getChildren();
    }
}
