package com.venherak.lab3;

import com.venherak.lab3.lexical.Parser;
import com.venherak.lab3.lexical.Token;

import java.util.HashSet;
import java.util.Set;

public class Main {

    private static Set<Token> delimiterList;
    private static Set<Token> operatorList;
    private static Set<Token> keywordList;

    public static void main(String[] args) {
        String expression = "b+=a[n]";
        System.out.println(expression);

        getCAlphabet();

        Parser parser = new Parser(expression, delimiterList, operatorList, keywordList);

        parser.parseCode();

        System.out.println(parser.toString());
    }

    private static void getCAlphabet() {
        delimiterList = new HashSet<>();

        delimiterList.add(new Token(" ", "DELIMITER"));
        delimiterList.add(new Token(",", "DELIMITER"));
        delimiterList.add(new Token(";", "DELIMITER"));
        delimiterList.add(new Token(" ", "DELIMITER"));
        delimiterList.add(new Token(".", "DELIMITER"));
        delimiterList.add(new Token("{", "DELIMITER"));
        delimiterList.add(new Token("}", "DELIMITER"));
        delimiterList.add(new Token("(", "DELIMITER"));
        delimiterList.add(new Token(")", "DELIMITER"));
        delimiterList.add(new Token("[", "DELIMITER"));
        delimiterList.add(new Token("]", "DELIMITER"));

        keywordList = new HashSet<>();

        keywordList.add(new Token("int", "KEYWORD"));
        keywordList.add(new Token("char", "KEYWORD"));
        keywordList.add(new Token("short", "KEYWORD"));
        keywordList.add(new Token("int", "KEYWORD"));
        keywordList.add(new Token("long", "KEYWORD"));
        keywordList.add(new Token("signed", "KEYWORD"));
        keywordList.add(new Token("unsigned", "KEYWORD"));
        keywordList.add(new Token("float", "KEYWORD"));
        keywordList.add(new Token("double", "KEYWORD"));
        keywordList.add(new Token("void", "KEYWORD"));
        keywordList.add(new Token("return", "KEYWORD"));
        keywordList.add(new Token("static", "KEYWORD"));
        keywordList.add(new Token("if", "KEYWORD"));
        keywordList.add(new Token("else", "KEYWORD"));
        keywordList.add(new Token("for", "KEYWORD"));
        keywordList.add(new Token("do", "KEYWORD"));
        keywordList.add(new Token("while", "KEYWORD"));

        operatorList = new HashSet<>();

        operatorList.add(new Token("+", "OPERATOR"));
        operatorList.add(new Token("-", "OPERATOR"));
        operatorList.add(new Token("*", "OPERATOR"));
        operatorList.add(new Token("/", "OPERATOR"));
        operatorList.add(new Token(">", "OPERATOR"));
        operatorList.add(new Token("<", "OPERATOR"));
        operatorList.add(new Token("=", "OPERATOR"));
        operatorList.add(new Token("==", "OPERATOR"));
        operatorList.add(new Token("+=", "OPERATOR"));
        operatorList.add(new Token("*=", "OPERATOR"));
        operatorList.add(new Token("/=", "OPERATOR"));
        operatorList.add(new Token("++", "OPERATOR"));
        operatorList.add(new Token("--", "OPERATOR"));

    }
}
