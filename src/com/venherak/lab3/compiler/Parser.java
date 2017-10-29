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

            token = new Token(word, "KEYWORD");
            if (keyWordList.contains(token)) {
                word = word.replace(word, "");
                performTableStep(token, word);
                word = "";
                continue;
            }
            token = new Token(letter.toString(), "DELIMITER");
            if (delimiterList.contains(token)) {
                word = word.replace(letter.toString(), "");
                performTableStep(token, word);
                word = "";
                continue;
            }
            token = new Token(letter.toString(), "OPERATOR");
            if (operationList.contains(token)) {
                word = word.replace(letter.toString(), "");
                performTableStep(token, word);
                word = "";
            }
        }
        if(word.length() != 0) {
            addToken(new Token(word, "IDENTIFIER"));
        }
    }

    public void performTableStep(Token token, String word) {
        if (word.length() > 0) {

            if (isNumeric(word)) {
                addToken(new Token(word, "CONSTANT"));
                addToLexemeSet(new Token(word, "CONSTANT"));
            } else {
                //if(isNumeric(word.substring(0,1))) {
                //} else {
                    addToken(new Token(word, "IDENTIFIER"));
                    addToLexemeSet(new Token(word, "IDENTIFIER"));
                //}
            }
        }
        addToken(token);
    }

    public void addToken(Token token) {
        lexemeTable.add(token);
    }

    public void addToLexemeSet(Token token) {
        if (token.getTitle().equals("CONSTANT")) {
            constantList.add(token);
        } else {
            if (token.getTitle().equals("IDENTIFIER")) {
                identifierList.add(token);
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Token token : this.lexemeTable) {
            result += token.toString() + "\n";
        }
        return result;
    }

    public static boolean isNumeric(String str) {
        try {
            double test = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
