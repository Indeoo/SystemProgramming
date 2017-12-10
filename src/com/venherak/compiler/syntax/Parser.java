package com.venherak.compiler.syntax;

import com.venherak.compiler.exceptions.SyntaxException;
import com.venherak.compiler.languages.Language;
import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;
import com.venherak.compiler.syntax.table.State;
import jdk.nashorn.internal.ir.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
    private SymbolChain terminals;
    private Language language;
    private State currentState;
    private List<State> states;

    public Parser(List<Token> tokens, Language language) {
        this.language = language;
        this.terminals = new SymbolChain();
        for (Token token : tokens) {
            terminals.add(new Terminal(token));
        }
        this.states = new ArrayList<>();
        currentState = new State(language);
        states.add(currentState);
    }

    public Parser(Language language) {
        this.language = language;
        terminals = new SymbolChain();
        this.states = new ArrayList<>();
        currentState = new State(language);
        states.add(currentState);
    }

    public void formTable() throws SyntaxException {
        currentState.formStates(states);
        states.get(1).setAccept(true);
        states.get(1).getItemList().get(0).setFinish(false);
        Stack stack = new Stack();
        stack.push(currentState);
/*        for (int i = 0; i < states.size(); i++) {
            System.out.println(states.get(i) + " " + i);
        }*/

        int i = 0;
        try {
            while (!currentState.isAccept()) {
                System.out.println(terminals.getHighTreeLayer());
/*            System.out.println(terminals.getHighTreeLayer());
            System.out.println(currentState);
            System.out.println(terminals.getHighTreeLayer().get(i) + "  sss "  );*/
                changeState(terminals.getHighTreeLayer().get(i));

                stack.push(terminals.getHighTreeLayer().get(i));
                stack.push(currentState);

                if (currentState.isFinishState()) {
                    SymbolChain symbols = new SymbolChain();
                    for (int j = 0; j < currentState.getItemList().get(0).getRule().getRight().size(); j++) {
                        changeState((State) stack.pop());
                        symbols.add(0, (AbstractSymbol) stack.pop());
                        i--;
                    }
                    changeState((State) stack.pop());
                    stack.push(currentState);
                    symbols.reduceChainByRule(language.getRules());
                }
                i++;
            }
        } catch (NullPointerException e) {
            stack.pop();
            AbstractSymbol symbol = (AbstractSymbol) stack.pop();
            State state = (State) stack.pop();
            throw  new SyntaxException("Error in " + symbol + " supposed to be " + state.getItemList());
        }
     //   System.out.println(terminals.getHighTreeLayer());
/*        System.out.println(states.get(4));
        changeState(terminals.get(0));
        System.out.println(currentState.getNextStates());

        System.out.println(states.get(0).getNextStates().get(2).getNextStates());*/
//        System.out.println(currentState.getNextStates());
/*        Stack stack = new Stack();
        stack.push(currentState);

        int i = 0;
        while (!currentState.isAccept()) {
            System.out.println(currentState);
            System.out.println(currentState.isAccept());
            changeState(terminals.getHighTreeLayer().get(i));
            System.out.println(terminals.getHighTreeLayer().get(i));
            System.out.println(currentState);

            stack.push(terminals.getHighTreeLayer().get(i));
            stack.push(currentState);
           *//* System.out.println(i);
            System.out.println(terminals.getHighTreeLayer());
            System.out.println(terminals.getHighTreeLayer().get(i));*//*

            if (currentState.isFinishState()) {
                SymbolChain symbols = new SymbolChain();
                for (int j = 0; j < currentState.getItemList().get(0).getRule().getRight().size(); j++) {
                    changeState((State) stack.pop());
                    symbols.add(0, (AbstractSymbol) stack.pop());
                    i--;
                }
                changeState((State) stack.pop());
                stack.push(currentState);
                symbols.reduceChainByRule(language.getRules());
            }
            i++;
        }
        System.out.println(terminals.getHighTreeLayer());*/
    }

    public void changeState(AbstractSymbol signal) {
        changeState(currentState.getNextBySignal(signal));
    }

    public void changeState(State state) {
        currentState = state;
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
                System.out.println(language.findRulesEnd(errorSequence));
                if (language.findRulesEnd(errorSequence).size() == 0) {
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
