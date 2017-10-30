package com.venherak.lab3;

import com.venherak.lab3.compiler.Parser;
import com.venherak.lab3.compiler.Token;

import java.util.HashSet;
import java.util.Set;

public class Main {

    private static Set<Token> delimiterList;
    private static Set<Token> operatorList;
    private static Set<Token> keywordList;

    public static void main(String[] args) {
        String expression = "int main (void) {float b,a[13];int n;...}";
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
        delimiterList.add(new Token(".", "DELIMITER"));
        delimiterList.add(new Token("{", "DELIMITER"));
        delimiterList.add(new Token("}", "DELIMITER"));
        delimiterList.add(new Token("(", "DELIMITER"));
        delimiterList.add(new Token(")", "DELIMITER"));
        delimiterList.add(new Token("[", "DELIMITER"));
        delimiterList.add(new Token("]", "DELIMITER"));

        keywordList = new HashSet<>();

        keywordList.add(new Token("int", "KEYWORD"));
        keywordList.add(new Token("float", "KEYWORD"));
        keywordList.add(new Token("string", "KEYWORD"));
        keywordList.add(new Token("void", "KEYWORD"));
        keywordList.add(new Token("return", "KEYWORD"));
        keywordList.add(new Token("static", "KEYWORD"));
        keywordList.add(new Token("if", "KEYWORD"));
        keywordList.add(new Token("for", "KEYWORD"));

        operatorList = new HashSet<>();

        operatorList.add(new Token("+", "OPERATION"));
        operatorList.add(new Token("-", "OPERATION"));
        operatorList.add(new Token("*", "OPERATION"));
        operatorList.add(new Token("/", "OPERATION"));
        operatorList.add(new Token(">", "OPERATION"));
        operatorList.add(new Token("<", "OPERATION"));
        operatorList.add(new Token("=", "OPERATION"));
        operatorList.add(new Token("==", "OPERATION"));
    }
}