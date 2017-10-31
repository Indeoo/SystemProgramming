package com.venherak.lab3.compiler;

import java.util.*;

public class Parser {
    private HashSet<Token> identifierList;
    private HashSet<Token> constantList;
    private Set<Token> delimiterList;
    private Set<Token> operationList;
    private Set<Token> keyWordList;
    private List<Token> lexemeTable;
    private String code;

    private static final String UNKNOWN_TOKEN = "UNKNOWN";
    private static final String IDENTIFIER_TOKEN = "IDENTIFIER";
    private static final String OPERATOR_TOKEN = "OPERATOR";
    private static final String DELIMITER_TOKEN = "DELIMITER";
    private static final String CONSTANT_TOKEN = "CONSTANT";
    private static final String KEYWORD_TOKEN = "KEYWORD";
    private static final String UNEXPECTED_TOKEN = "UNEXPECTED SYMBOL";

    public Parser(String code, Set<Token> delimiterList, Set<Token> operationList, Set<Token> keyWordList) {
        this.code = code;

        this.identifierList = new HashSet<>();
        this.constantList = new HashSet<>();
        this.delimiterList = delimiterList;
        this.operationList = operationList;
        this.keyWordList = keyWordList;
        lexemeTable = new ArrayList<>();
    }

    public void parseCode() {
        char[] chars = code.toCharArray();
        String word = "";
        Token token;
        for (Character letter : chars) {
            word += letter;
            if (checkIfAllowedString(letter.toString())) {
                token = new Token(word, KEYWORD_TOKEN);
                if (keyWordList.contains(token)) {
                    word = word.replace(word, "");
                    performTableStep(word);
                    addToken(token);
                    word = "";
                    continue;
                }
                token = new Token(letter.toString(), DELIMITER_TOKEN);
                if (delimiterList.contains(token)) {
                    word = word.replace(letter.toString(), "");
                    performTableStep(word);
                    addToken(token);
                    word = "";
                    continue;
                }
                token = new Token(letter.toString(), OPERATOR_TOKEN);
                if (operationList.contains(token)) {
                    word = word.replace(letter.toString(), "");
                    performTableStep(word);
                    addToken(token);
                    word = "";
                }
            } else {
                word = word.replace(letter.toString(), "");
                performTableStep(word);
                word = word.replace(word, "");
                lexemeTable.add(new Token(letter.toString(), UNEXPECTED_TOKEN));
            }
        }
        if (word.length() != 0) {
            performTableStep(word);
        }
        concatNeighborOperators();
    }

    private void concatNeighborOperators() {
        Token token;
        Token prevToken = new Token("", "");
        Stack stack = new Stack();
        for (int i = 0; i < lexemeTable.size(); i++) {
            token = lexemeTable.get(i);
            if (token.getTitle().equals(OPERATOR_TOKEN) && prevToken.getTitle().equals(OPERATOR_TOKEN) && checkTokenByString(prevToken.getSign() + token.getSign())) {
                String doubleOperator = prevToken.getSign() + token.getSign();
                lexemeTable.set(i, new Token(doubleOperator, OPERATOR_TOKEN));
                stack.push(i - 1);
            }
            prevToken = token;
        }
        while (!stack.empty()) {
            lexemeTable.remove((int) stack.pop());
        }
    }

    private void performTableStep(String word) {
        if (word.length() > 0) {
            if (checkIfNumericString(word)) {
                addToken(new Token(word, CONSTANT_TOKEN));
            } else {
                if (checkIfNumericString(word.substring(0, 1))) {
                    addToken(new Token(word, UNKNOWN_TOKEN));
                } else {
                    addToken(new Token(word, IDENTIFIER_TOKEN));
                }
            }
        }
    }

    private void addToken(Token token) {
        lexemeTable.add(token);
        addToLexemeSet(token);
    }

    private void addToLexemeSet(Token token) {
        if (token.getTitle().equals(CONSTANT_TOKEN)) {
            constantList.add(token);
        } else {
            if (token.getTitle().equals(IDENTIFIER_TOKEN)) {
                identifierList.add(token);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (Token token : this.lexemeTable) {
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
        for (Token token : operationList) {
            if (token.getSign().equals(string)) {
                return true;
            }
        }
        return false;
    }
}