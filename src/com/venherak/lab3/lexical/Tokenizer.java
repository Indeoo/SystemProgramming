package com.venherak.lab3.lexical;

import com.venherak.lab3.Exceptions.WrongTokenException;
import com.venherak.lab3.languages.Language;

import java.util.*;

public class Tokenizer {
    private Set<Token> delimiterSet;
    private Set<Token> operationSet;
    private Set<Token> keyWordSet;
    private Set<Token> identifierSet;
    private Set<Token> constantSet;
    private List<Token> lexemeTableList;
    private String code;
    private Language language;

    private static final String WRONG_TOKEN = "Wrong Identifier";
    private static final String IDENTIFIER_TOKEN = "Identifier";
    private static final String OPERATOR_TOKEN = "Operator";
    private static final String STATE_OPERATOR_TOKEN = "StateOperator";
    private static final String DELIMITER_TOKEN = "Delimiter";
    private static final String CONSTANT_TOKEN = "Constant";
    private static final String KEYWORD_TOKEN = "Keyword";
    private static final String TYPE_TOKEN = "Type";
    private static final String BRACKET_TOKEN = "Bracket";
    private static final String UNEXPECTED_TOKEN = "UNEXPECTED SYMBOL";

    public Tokenizer(String code, Language language) {
        this.code = code;
        this.identifierSet = new HashSet<>();
        this.constantSet = new HashSet<>();
        this.delimiterSet = new HashSet<>();
        this.operationSet = new HashSet<>();
        this.keyWordSet = new HashSet<>();
        this.language = language;
        this.language.getLemexeAlphabet(delimiterSet, operationSet, keyWordSet);
        lexemeTableList = new ArrayList<>();
    }

    public void formTokens() throws WrongTokenException {
        char[] chars = code.toCharArray();
        String word = "";
        for (Character letter : chars) {
            word += letter;
            if (language.checkIfAllowedString(letter.toString())) {
                boolean operation = operationSet.contains(new Token(letter.toString(), OPERATOR_TOKEN));
                boolean delimiter = delimiterSet.contains(new Token(letter.toString(), DELIMITER_TOKEN));
                boolean bracket = delimiterSet.contains(new Token(letter.toString(), BRACKET_TOKEN));

                if ((delimiter || operation || bracket)) {
                    word = word.replace(letter.toString(), "");
                    performTableStep(word);
                    addTokenToLexemeList(findTokenBySign(letter.toString()));
                    word = "";
                }
            } else {
                throw new WrongTokenException("Wrong Token! " + new Token(letter.toString(), "SYMBOL IS NOT ALLOWED"));
            }
        }
        if (word.length() != 0) {
            performTableStep(word);
        }
        concatDoubleOperators();


    }

    private void performTableStep(String word) throws WrongTokenException {
        if (keyWordSet.contains(new Token(word, KEYWORD_TOKEN))) {
            addTokenToLexemeList(new Token(word, KEYWORD_TOKEN));
        } else {
            if (keyWordSet.contains(new Token(word, TYPE_TOKEN))) {
                addTokenToLexemeList(new Token(word, TYPE_TOKEN));
            } else {
                if (word.length() > 0) {
                    if (checkIfNumericString(word)) {
                        addTokenToLexemeList(new Token(word, CONSTANT_TOKEN));
                    } else {
                        if (checkIfNumericString(word.substring(0, 1))) {
                            addTokenToLexemeList(new Token(word, WRONG_TOKEN));
                            throw new WrongTokenException("Wrong Token! " + new Token(word, WRONG_TOKEN));
                        } else {
                            addTokenToLexemeList(new Token(word, IDENTIFIER_TOKEN));
                        }
                    }
                }
            }
        }
    }

    private void concatDoubleOperators() {
        Token token;
        Token prevToken = new Token("", "");
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < lexemeTableList.size(); i++) {
            token = lexemeTableList.get(i);

            if (token.getType().equals(OPERATOR_TOKEN)
                    && prevToken.getType().equals(OPERATOR_TOKEN)
                    && checkTokenByString(prevToken.getSign() + token.getSign())
                    && !token.getSign().equals("=")) {
                String doubleOperator = prevToken.getSign() + token.getSign();
                lexemeTableList.set(i, new Token(doubleOperator, OPERATOR_TOKEN));
                stack.push(i - 1);
            } else {
                if (token.getType().equals(OPERATOR_TOKEN)
                        && prevToken.getType().equals(OPERATOR_TOKEN)
                        && checkTokenByString(prevToken.getSign() + token.getSign())) {
                    String doubleOperator = prevToken.getSign() + token.getSign();
                    lexemeTableList.set(i, new Token(doubleOperator, STATE_OPERATOR_TOKEN));
                    stack.push(i - 1);
                }
            }
            prevToken = token;
        }
        while (!stack.empty()) {
            lexemeTableList.remove((int) stack.pop());
        }

        for(Token token1: lexemeTableList) {
            if(token1.getSign().equals("=")) {
                token1.setType(STATE_OPERATOR_TOKEN);
            }
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

    private boolean checkIfNumericString(String str) {
        return str.matches("^[0-9]+$");
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (Token token : this.lexemeTableList) {
            result.append(token.toString()).append("\n");
        }
        return result.toString();
    }
}