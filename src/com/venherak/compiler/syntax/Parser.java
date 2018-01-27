package com.venherak.compiler.syntax;

import com.venherak.compiler.exceptions.SemanticException;
import com.venherak.compiler.exceptions.SyntaxException;
import com.venherak.compiler.languages.Language;
import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;
import com.venherak.compiler.syntax.table.State;

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
            if (!token.getSign().equals(" ")) {
                terminals.add(new Terminal(token));
            }
        }
        states = new ArrayList<>();
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

    public void parse() throws SyntaxException {
        currentState.formStates(states);
        states.get(1).setAccept(true);
        states.get(1).getItemList().get(0).setFinish(false);
        Stack stack = new Stack();
        stack.push(currentState);

        try {
            int i = 0;
            SymbolChain symbols = new SymbolChain();

            while (!currentState.isAccept()) {
                changeState(terminals.getHighTreeLayer().get(i));
                stack.push(terminals.getHighTreeLayer().get(i));
                stack.push(currentState);
                if (currentState.isFinishState()) {
                    symbols.clear();
                    int ruleSize = currentState.getItemList().get(0).getRule().getRight().size();
                    for (int j = 0; j < ruleSize; j++) {
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
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            stack.pop();
            AbstractSymbol symbol = (AbstractSymbol) stack.pop();
            State state = (State) stack.pop();
            throw new SyntaxException("Error in \"" + symbol + "\" supposed to be " + state.getItemList(), e);
        }
    }

    public void analyzeSemantics() throws SemanticException {
        List<String> identifiers = new ArrayList<>();
        List<String> type = new ArrayList<>();
        for (int i = 0; i < terminals.size(); i++) {
            if (terminals.get(i).getParent().getLiteral().equals("Identifier")) {
                if (terminals.get(i).getParent().getParent().getLiteral().equals("Declaration")) {
                    identifiers.add(terminals.get(i).getLiteral());
                    type.add(getLeftLeaf(getNonTerminal(terminals.get(i), "Statements")).getLiteral());
                }
                int identifierCounter = 0;
                for (String identifier : identifiers) {
                    if (identifier.equals(terminals.get(i).getLiteral())) {
                        identifierCounter++;
                    }
                }

                if (identifierCounter != 1) {
                    if (identifierCounter == 0) {
                        throw new SemanticException("No variable declared: " + terminals.get(i));
                    }
                    if (identifierCounter > 1) {
                        throw new SemanticException("More that 1 declaration of variable: " + terminals.get(i));
                    }
                }
            }
            if (terminals.get(i).getParent().getLiteral().equals("Operator") || terminals.get(i).getParent().getLiteral().equals("StateOperator")) {
                String leftLexeme = null;
                String rightLexeme = null;

                for (int j = 0; j < identifiers.size(); j++) {
                    if (identifiers.get(j).equals(terminals.get(i - 1).getLiteral())) {
                        leftLexeme = type.get(j);
                    }
                    if (identifiers.get(j).equals(terminals.get(i + 1).getLiteral())) {
                        rightLexeme = type.get(j);
                    }
                }
                if (leftLexeme != null && !leftLexeme.equals(rightLexeme)) {
                    throw new SemanticException("Type mismatch");
                }
            }
        }
    }

    private AbstractSymbol getLeftLeaf(AbstractSymbol symbol) {
        if (symbol.getChildren().size() == 0) {
            return symbol;
        } else {
            return getLeftLeaf(symbol.getChildren().get(0));
        }
    }

    private AbstractSymbol getNonTerminal(AbstractSymbol symbol, String nonTerminal) {
        if (symbol.getParent() != null) {
            if (symbol.getParent().getLiteral().equals(nonTerminal)) {
                return symbol.getParent();
            } else {
                return getNonTerminal(symbol.getParent(), nonTerminal);
            }
        } else {
            return symbol;
        }
    }

    private void changeState(AbstractSymbol signal) {
        changeState(currentState.getNextBySignal(signal));
    }

    private void changeState(State state) {
        currentState = state;
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