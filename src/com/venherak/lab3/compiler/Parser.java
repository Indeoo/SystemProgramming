package com.venherak.lab3.compiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {
    private Set<Token> identifierList;
    private Set<Token> constantList;
    private Set<Token> delimiterList;
    private Set<Token> operationList;
    private Set<Token> keyWordList;
    private List<Token> lexemeTable;
    private String code;

    private static final String UNKNOWN_TOKEN = "Unknown";
    private static final String IDENTIFIER_TOKEN = "IDENTIFIER";
    private static final String OPERATOR_TOKEN = "OPERATOR";
    private static final String DELIMITER_TOKEN = "DELIMITER";
    private static final String CONSTANT_TOKEN = "CONSTANT";

    public Parser(String code, Set<Token> delimiterList, Set<Token> operationList, Set<Token> keyWordList) {
        this.code = code;

        this.identifierList = new HashSet();
        this.constantList = new HashSet();
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
                token = new Token(word, "KEYWORD");
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
                token = new Token(letter.toString(), "UNEXPECTED SYMBOL");
                lexemeTable.add(token);
            }
        }
        if (word.length() != 0) {
            performTableStep(word);
        }
        concatNeighborOperators();
    }

    private void concatNeighborOperators() {
        Token token;
        Token prevToken = new Token("filler", "filler");
        for (int i = 0; i < lexemeTable.size(); i++) {
            token = lexemeTable.get(i);
            if (token.getTitle().equals(OPERATOR_TOKEN) && prevToken.getTitle().equals(OPERATOR_TOKEN)) {
                String doubleOperator = token.getSign() + prevToken.getSign();
                lexemeTable.set(i, new Token(doubleOperator, OPERATOR_TOKEN));
                lexemeTable.remove(i - 1);
            }
            prevToken = token;
        }
    }
    private void performTableStep(String word) {
        if (word.length() > 0) {
            if (checkIfNumericString(word)) {
                System.out.println(word);
                addToken(new Token(word, CONSTANT_TOKEN));
                addToLexemeSet(new Token(word, CONSTANT_TOKEN));
            } else {
                if (checkIfNumericString(word.substring(0, 1))) {
                    addToken(new Token(word, UNKNOWN_TOKEN));
                } else {
                    addToken(new Token(word, IDENTIFIER_TOKEN));
                    addToLexemeSet(new Token(word, IDENTIFIER_TOKEN));
                }
            }
        }
    }

    private void addToken(Token token) {
        lexemeTable.add(token);
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
            result.append(token.toString()).append( "\n");
        }
        return result.toString();
    }

    private boolean checkIfNumericString(String str) {
        return str.matches("^[0-9]+$");
    }
    private boolean checkIfAllowedString(String string) {
        return string.matches("[a-zA-Z0-9=+*/%;&|\\[\\]_(),\\\"'.<> {}-]+$");
    }
    boolean checkToken(Token tokenIN) {
        for (Token token : lexemeTable) {
            if (token.getSign().equals(tokenIN.getSign())) {
                return true;
            }
        }
        return false;
    }
}