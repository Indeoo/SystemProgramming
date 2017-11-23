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
    public static final String DELIMITER = "Delimiter";
    public static final String EXPRESSION = "Expression";

    private static Set<Token> delimiterList;
    private static Set<Token> operatorList;
    private static Set<Token> keywordList;
    private static List<Rule> rules;
    private static List<Terminal> terminals;
    private static List<NonTerminal> nonTerminals;
    private static NonTerminal root;

    public static void main(String[] args) {
        String expression = "a+=b[n]";
        System.out.println(expression + "\n");

        getCLexemeAlphabet();

        Parser parser = new Parser(expression, delimiterList, operatorList, keywordList);

        parser.parseCode();

        System.out.println(parser.toString());
        getRules();
        root = new NonTerminal("PROGRAM");
        rules.addAll(tokenRules(parser.getLexemeTableList()));
        Language language = new Language(rules, terminals, nonTerminals, root);
        language.viewRules();

        ParseTree parseTree = new ParseTree(language, parser.getLexemeTableList());

        parseTree.formTree();
        parseTree.viewTree(root);
    }

    static void getRules() {
        NonTerminal STATEMENT = new NonTerminal("STATEMENT");
        NonTerminal EXPRESSION = new NonTerminal(Main.EXPRESSION);
        NonTerminal DELIMITER = new NonTerminal(Main.DELIMITER);
        NonTerminal IDENTIFIER = new NonTerminal("Identifier");
        NonTerminal OPERATOR = new NonTerminal("Operator");
        NonTerminal CONSTANT = new NonTerminal("Constant");
        NonTerminal BRACKETS = new NonTerminal("Brackets");
        Terminal squareDelimLeft = new Terminal("[");
        Terminal squareDelimRIght = new Terminal("]");
        terminals = new ArrayList<>();
        terminals.add(squareDelimLeft);
        terminals.add(squareDelimRIght);
        nonTerminals = new ArrayList<>();
        nonTerminals.add(STATEMENT);
        nonTerminals.add(EXPRESSION);
        nonTerminals.add(DELIMITER);
        nonTerminals.add(IDENTIFIER);
        nonTerminals.add(OPERATOR);
        nonTerminals.add(CONSTANT);
        nonTerminals.add(BRACKETS);

        List<Rule> ruleList = new ArrayList<>();
        Rule eRule1 = new Rule(EXPRESSION, new SymbolSequence());
        eRule1.getRight().add(IDENTIFIER);
        Rule eRule2 = new Rule(EXPRESSION, new SymbolSequence());
        eRule2.getRight().add(IDENTIFIER);
        eRule2.getRight().add(OPERATOR);
        eRule2.getRight().add(EXPRESSION);
        Rule eRule3 = new Rule(EXPRESSION, new SymbolSequence());
        eRule3.getRight().add(IDENTIFIER);
        eRule3.getRight().add(squareDelimLeft);
        eRule3.getRight().add(EXPRESSION);
        eRule3.getRight().add(squareDelimRIght);
        Rule rule4 = new Rule(EXPRESSION, new SymbolSequence());
        rule4.getRight().add(CONSTANT);
        rule4.getRight().add(OPERATOR);
        rule4.getRight().add(EXPRESSION);
        Rule rule5 = new Rule(EXPRESSION, new SymbolSequence());
        rule5.getRight().add(CONSTANT);
        //Rule rule6 = new Rule(EXPRESSION, new SymbolSequence());
       // rule6.getRight().add(EXPRESSION);
       // rule6.getRight().add(DELIMITER);
        ruleList.add(eRule1);
        ruleList.add(eRule2);
        ruleList.add(eRule3);
        ruleList.add(rule4);
        ruleList.add(rule5);
        //ruleList.add(rule6);
        root = EXPRESSION;
        rules = new ArrayList<>();
        rules.addAll(ruleList);
    }

    static List<Rule> tokenRules(List<Token> tokens) {
        List<Rule> rules = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getType().equals("Identifier")) {
                rules.add(new Rule(new NonTerminal("Identifier"), new Terminal(token)));
                terminals.add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("Operator")) {
                rules.add(new Rule(new NonTerminal("Operator"), new Terminal(token)));
                terminals.add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("Constant")) {
                rules.add(new Rule(new NonTerminal("Constant"), new Terminal(token)));
                terminals.add(new Terminal(token.getSign()));
            }
/*            if (token.getType().equals(DELIMITER)) {
                rules.add(new Rule(new NonTerminal(DELIMITER), new Terminal(token)));
                terminals.add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("Brackets")) {
                rules.add(new Rule(new NonTerminal("Brackets"), new Terminal(token)));
                terminals.add(new Terminal(token.getSign()));
            }*/
        }
        return rules;
    }

    private static void getCLexemeAlphabet() {
        delimiterList = new HashSet<>();

        delimiterList.add(new Token(" ", DELIMITER));
        delimiterList.add(new Token(",", DELIMITER));
        delimiterList.add(new Token(";", DELIMITER));
        delimiterList.add(new Token(" ", DELIMITER));
        delimiterList.add(new Token(".", DELIMITER));

        delimiterList.add(new Token("{", DELIMITER));
        delimiterList.add(new Token("}", DELIMITER));
        delimiterList.add(new Token("(", DELIMITER));
        delimiterList.add(new Token(")", DELIMITER));
        delimiterList.add(new Token("[", DELIMITER));
        delimiterList.add(new Token("]", DELIMITER));

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

        operatorList.add(new Token("+", "Operator"));
        operatorList.add(new Token("-", "Operator"));
        operatorList.add(new Token("*", "Operator"));
        operatorList.add(new Token("/", "Operator"));
        operatorList.add(new Token(">", "Operator"));
        operatorList.add(new Token("<", "Operator"));
        operatorList.add(new Token("=", "Operator"));
        operatorList.add(new Token("==", "Operator"));
        operatorList.add(new Token("+=", "Operator"));
        operatorList.add(new Token("*=", "Operator"));
        operatorList.add(new Token("/=", "Operator"));
        operatorList.add(new Token("++", "Operator"));
        operatorList.add(new Token("--", "Operator"));
    }
}
