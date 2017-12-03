package com.venherak.compiler.syntax;

import com.venherak.compiler.exceptions.SyntaxException;
import com.venherak.compiler.languages.Language;
import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

import java.util.List;

public class Parser {
    private SymbolChain terminals;
    private Language language;
    private State state;
    private List<State> states;

    public Parser(List<Token> tokens, Language language) {
        this.language = language;
        this.terminals = new SymbolChain();
        for (Token token : tokens) {
            terminals.add(new Terminal(token));
        }
    }

    public Parser(Language language) {
        this.language = language;
        terminals = new SymbolChain();
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
                System.out.println(symbolChain);
                System.out.println(symbolChain.getHighTreeLayer() + " \n");
            }
        }
        System.out.println("Higher Tree Layer: " + terminals.getHighTreeLayer());
        System.out.println();
        checkOnErrors();
    }

    private void checkOnErrors() throws SyntaxException {
        SymbolChain symbols = terminals.getHighTreeLayer();
        StringBuilder errorMsg = new StringBuilder("Syntax exception\n");
        SymbolChain errorSequence = new SymbolChain();
        errorSequence.add(symbols.get(symbols.size() - 1));

        if (symbols.size() > 1) {
            for (int i = symbols.size() - 1; i >= 0; i--) {
                System.out.println(language.findRules(errorSequence));
                if (language.findRules(errorSequence).size() == 0) {
                    errorMsg.append(errorSequence.get(errorSequence.size() - 1)).append(" - EXTRA SYMBOL(S)\n");
                    errorSequence = new SymbolChain();
                } else {
                    errorSequence.add(0, symbols.get(i));
                }
            }
            throw new SyntaxException(errorMsg.toString());
        }
    }

    public void viewTree(AbstractSymbol symbol) {
        Integer i = 0;
        viewTree(symbol, i);
    }

    private void viewTree(AbstractSymbol symbol, Integer i) {
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
