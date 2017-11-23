package com.venherak.lab3;

import com.venherak.lab3.lexical.Parser;
import com.venherak.lab3.lexical.Token;
import com.venherak.lab3.syntax.Language;
import com.venherak.lab3.syntax.ParseTree;
import com.venherak.lab3.syntax.Rule;
import com.venherak.lab3.syntax.alphabet.NonTerminal;
import com.venherak.lab3.syntax.alphabet.SymbolSequence;
import com.venherak.lab3.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    private static Set<Token> delimiterList;
    private static Set<Token> operatorList;
    private static Set<Token> keywordList;
    private static List<Rule> rules;
    private static List<Terminal> terminals;
    private static List<NonTerminal> nonTerminals;
    private static NonTerminal root;

    public static void main(String[] args) {
        String expression = "sdfa+=b[n]]]]";
        System.out.println(expression + "\n");

        getCAlphabet();

        Parser parser = new Parser(expression, delimiterList, operatorList, keywordList);

        parser.parseCode();

        System.out.println(parser.toString());
        getRules();
        root  = new NonTerminal("S");
        Language language = new Language(rules, terminals, nonTerminals, root);
        language.viewRuels();

        ParseTree parseTree = new ParseTree(language, parser.getLexemeTableList());

        parseTree.formTree();
        parseTree.viewTree(root);
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

    static void getRules() {
        NonTerminal E = new NonTerminal("E");
        NonTerminal T = new NonTerminal("T");
        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");
        Terminal n = new Terminal("n");
        Terminal plusEqual = new Terminal("+=");
        Terminal squareDelimLeft = new Terminal("[");
        Terminal squareDelimRIght = new Terminal("]");
        terminals = new ArrayList<>();
        terminals.add(a);
        terminals.add(b);
        terminals.add(n);
        terminals.add(squareDelimLeft);
        terminals.add(squareDelimRIght);
        terminals.add(plusEqual);
        nonTerminals = new ArrayList<>();
        nonTerminals.add(E);
        nonTerminals.add(T);

        List<Rule> eRuleList = new ArrayList<>();
        Rule eRule1 = new Rule(E, new SymbolSequence());
        eRule1.getRight().add(T);
        Rule eRule2 = new Rule(E, new SymbolSequence());
        eRule2.getRight().add(T);
        eRule2.getRight().add(plusEqual);
        eRule2.getRight().add(E);
        Rule eRule3 = new Rule(E, new SymbolSequence());
        eRule3.getRight().add(T);
        eRule3.getRight().add(squareDelimLeft);
        eRule3.getRight().add(E);
        eRule3.getRight().add(squareDelimRIght);
        eRuleList.add(eRule1);
        eRuleList.add(eRule2);
        eRuleList.add(eRule3);

        List<Rule> tRuleList = new ArrayList<>();
        Rule tRule1 = new Rule(T, new SymbolSequence());
        tRule1.getRight().add(a);
        Rule tRule2 = new Rule(T, new SymbolSequence());
        tRule2.getRight().add(b);
        Rule tRule3 = new Rule(T, new SymbolSequence());
        tRule3.getRight().add(n);
        tRuleList.add(tRule1);
        tRuleList.add(tRule2);
        tRuleList.add(tRule3);
        root = E;
        rules = new ArrayList<>();
        rules.addAll(eRuleList);
        rules.addAll(tRuleList);
    }
}
