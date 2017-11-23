package com.venherak.lab3.lexical;

import java.util.*;

public class Parser {
    private Set<Token> delimiterSet;
    private Set<Token> operationSet;
    private Set<Token> keyWordSet;
    private Set<Token> identifierSet;
    private Set<Token> constantSet;
    private List<Token> lexemeTableList;
    private String code;

    private static final String UNKNOWN_TOKEN    = "UNKNOWN";
    private static final String IDENTIFIER_TOKEN = "IDENTIFIER";
    private static final String OPERATOR_TOKEN   = "OPERATOR";
    private static final String DELIMITER_TOKEN  = "DELIMITER";
    private static final String CONSTANT_TOKEN   = "CONSTANT";
    private static final String KEYWORD_TOKEN    = "KEYWORD";
    private static final String UNEXPECTED_TOKEN = "UNEXPECTED SYMBOL";

    public Parser(String code, Set<Token> delimiterSet, Set<Token> operationSet, Set<Token> keyWordSet) {
        this.code = code;
        this.identifierSet = new HashSet<>();
        this.constantSet = new HashSet<>();
        this.delimiterSet = delimiterSet;
        this.operationSet = operationSet;
        this.keyWordSet = keyWordSet;
        lexemeTableList = new ArrayList<>();
    }

    public void parseCode() {
        char[] chars = code.toCharArray();
        String word = "";
        for (Character letter : chars) {
            word += letter;
            if (checkIfAllowedString(letter.toString())) {
                boolean operation = operationSet.contains(new Token(letter.toString(), OPERATOR_TOKEN));
                boolean delimiter = delimiterSet.contains(new Token(letter.toString(), DELIMITER_TOKEN));
                if ((delimiter || operation)) {
                    word = word.replace(letter.toString(), "");
                    performTableStep(word);
                    addTokenToLexemeList(findTokenBySign(letter.toString()));
                    word = "";
                }
            } else {
                word = word.replace(letter.toString(), "");
                performTableStep(word);
                word = "";
                lexemeTableList.add(new Token(letter.toString(), UNEXPECTED_TOKEN));
            }
        }
        if (word.length() != 0) {
            performTableStep(word);
        }
        concatNeighborOperators();
    }

    private void performTableStep(String word) {
        if (keyWordSet.contains(new Token(word, KEYWORD_TOKEN))) {
            addTokenToLexemeList(new Token(word, KEYWORD_TOKEN));
        } else {
            if (word.length() > 0) {
                if (checkIfNumericString(word)) {
                    addTokenToLexemeList(new Token(word, CONSTANT_TOKEN));
                } else {
                    if (checkIfNumericString(word.substring(0, 1))) {
                        addTokenToLexemeList(new Token(word, UNKNOWN_TOKEN));
                    } else {
                        addTokenToLexemeList(new Token(word, IDENTIFIER_TOKEN));
                    }
                }
            }
        }
    }

    private void concatNeighborOperators() {
        Token token;
        Token prevToken = new Token("", "");
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < lexemeTableList.size(); i++) {
            token = lexemeTableList.get(i);
            if (token.getType().equals(OPERATOR_TOKEN)
                    && prevToken.getType().equals(OPERATOR_TOKEN)
                    && checkTokenByString(prevToken.getSign() + token.getSign())) {
                String doubleOperator = prevToken.getSign() + token.getSign();
                lexemeTableList.set(i, new Token(doubleOperator, OPERATOR_TOKEN));
                stack.push(i - 1);
            }
            prevToken = token;
        }
        while (!stack.empty()) {
            lexemeTableList.remove((int) stack.pop());
        }
    }

    private void addTokenToLexemeList(Token token) {
        lexemeTableList.add(token);
        addToLexemeSet(token);
    }

    private void addToLexemeSet(Token token) {
        if (token.getType().equals(CONSTANT_TOKEN)) {
            constantSet.add(token);
        } else {
            if (token.getType().equals(IDENTIFIER_TOKEN)) {
                identifierSet.add(token);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (Token token : this.lexemeTableList) {
            result.append(token.toString()).append("\n");
        }
        return result.toString();
    }

    private boolean checkIfNumericString(String str) {
        return str.matches("^[0-9]+$");
    }

    private boolean checkIfAllowedString(String string) {
        return string.matches("[a-zA-Z0-9=+*/%;&|\\[\\]_(),\\\"'.<> {}-]+$");
    }

    private boolean checkTokenByString(String string) {
        for (Token token : operationSet) {
            if (token.getSign().equals(string)) {
                return true;
            }
        }
        return false;
    }

    private Token findTokenBySign(String string) {
        for (Token token : getAllTokens()) {
            if (token.getSign().equals(string)) {
                return token;
            }
        }
        return null;
    }

    private Set<Token> getAllTokens() {
        Set allTokensSet = new HashSet<>();
        allTokensSet.addAll(delimiterSet);
        allTokensSet.addAll(operationSet);
        allTokensSet.addAll(keyWordSet);
        allTokensSet.addAll(identifierSet);
        allTokensSet.addAll(constantSet);
        return allTokensSet;
    }

    public List<Token> getLexemeTableList() {
        return lexemeTableList;
    }
}