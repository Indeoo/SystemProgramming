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
        //String expression = "a+=b[n]";
        String expression = "int main (void) {flo_at b,a[13];int 3n...8968sd6f873;3;n;n3}@;i++;b+=sd123;123";
       // String expression = "((c*d)(s*a)(1*s)+1)(a*b) i++";
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
