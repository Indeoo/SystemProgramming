package com.venherak.lab3;

import com.venherak.lab3.compiler.Parser;
import com.venherak.lab3.compiler.Token;

import java.util.HashSet;
import java.util.Set;

public class Main {
    //private static String expression = "int main (void) {float b,a[13];int n;...}";
    //private static String expression = "static void Result(string temp){for (int j = 0; j < reservedWords.Length; j++){if (temp == reservedWords[j]){for (int i = 0; i < tableR.Count; i++){if (temp == tableR[i]){return;";
    private static String expression = "1kek kekos";
    static Set<Token> delimiterList;
    static Set<Token> operatorList;
    static Set<Token> keywordList;

    public static void main(String[] args) {
        System.out.println(expression);

        getCAlphabet();

        Parser parser = new Parser(expression, delimiterList, operatorList, keywordList);

        parser.parseCode();

        System.out.println(parser.toString());
    }
    public static void getCAlphabet() {
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
