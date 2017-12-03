package com.venherak.lab3.syntax;

import com.venherak.lab3.Exceptions.SyntaxException;
import com.venherak.lab3.Language;
import com.venherak.lab3.lexical.Token;
import com.venherak.lab3.syntax.alphabet.AbstractSymbol;
import com.venherak.lab3.syntax.alphabet.NonTerminal;
import com.venherak.lab3.syntax.alphabet.SymbolChain;
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
        SymbolChain symbolChain = null;
        for (int i = terminals.size() - 1; i >= 0; i--) {
            for (int j = i; j < terminals.size(); j++) {
                symbolChain = new SymbolChain();
                for (int d = terminals.size() - j; d > 0; d--) {
                    symbolChain.add(terminals.get(i + terminals.size() - j - d));
                }
                if (transformSeqByRule(symbolChain)) {
                    i++;
                }
              //  System.out.println(symbolChain);
                //System.out.println(getHighTreeLayer(symbolChain) + " \n");
            }
        }

       // System.out.println(getHighTreeLayer(symbolChain));
      //  System.out.println(terminals);

        List<SymbolChain> errorList = new ArrayList<>();
        SymbolChain deathPenalty = new SymbolChain();

        boolean flag = true;
        for (Terminal terminal : terminals) {
            if (terminal.getParent() != null) {
                if (terminal.getRoot().getLiteral().equals("Statements")) {
                    terminal.getRoot().addParent(language.getRoot());
                    flag = true;
                } else {
                    if (!terminal.getRoot().getLiteral().equals("ROOT")) {
                        deathPenalty.add(terminal);
                        if (flag) {
                            errorList.add(deathPenalty);
                            deathPenalty = new SymbolChain();
                            flag = false;
                        }
                    }
                }
            } else {
                deathPenalty.add(terminal);
                if (flag) {
                    errorList.add(deathPenalty);
                    deathPenalty = new SymbolChain();
                    flag = false;
                }
            }
        }

        if (errorList.size() > 0) {
            throw new SyntaxException("Syntax errors: " + errorList);
        }

    }

    String formErrors(SymbolChain symbols) {
        String result = "";
        for (AbstractSymbol symbol : symbols) {
            result += symbol + "\n";
        }
        return result;
    }

    private boolean transformSeqByRule(SymbolChain symbolChain) {
        symbolChain = getHighTreeLayer(symbolChain);
        for (Rule rule : language.getRules()) {
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

    SymbolChain getRoots(SymbolChain symbolChain) {
        SymbolChain serviceSequence = new SymbolChain();
        for (AbstractSymbol symbol : symbolChain) {
            serviceSequence.add(symbol.getRoot());
        }
        return serviceSequence;
    }

    SymbolChain getHighTreeLayer(SymbolChain symbolChain) {
        symbolChain = getRoots(symbolChain);
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
