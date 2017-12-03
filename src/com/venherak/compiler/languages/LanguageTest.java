package com.venherak.compiler.languages;

import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.NonTerminal;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LanguageTest extends Language {
    @Override
    public void formRules() {
        List<Rule> ruleList = new ArrayList<>();

        NonTerminal ROOT = new NonTerminal("ROOT");
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");

        Rule rule1 = new Rule(ROOT, new SymbolChain());
        rule1.getRight().add(S);
        Rule rule2 = new Rule(S , new SymbolChain());
        rule2.getRight().add(A);
        rule2.getRight().add(A);

        Rule rule3 = new Rule(A, new SymbolChain());
        rule3.getRight().add(A);
        rule3.getRight().add(new Terminal("a"));

        Rule rule4 = new Rule(A, new SymbolChain());
        rule4.getRight().add(new Terminal("b"));

        ruleList.add(rule1);
        ruleList.add(rule2);
        ruleList.add(rule3);
        ruleList.add(rule4);

        getRules().addAll(ruleList);
    }

    public void getLemexeAlphabet(Set<Token> delimiterList, Set<Token> operatorList, Set<Token> keywordList) {
        String DELIMITER = "Delimiter";

        delimiterList.add(new Token(" ", DELIMITER));
        delimiterList.add(new Token(",", DELIMITER));
        delimiterList.add(new Token(";", DELIMITER));
        delimiterList.add(new Token(" ", DELIMITER));
        delimiterList.add(new Token(".", DELIMITER));

        delimiterList.add(new Token("{", "Bracket"));
        delimiterList.add(new Token("}", "Bracket"));
        delimiterList.add(new Token("(", "Bracket"));
        delimiterList.add(new Token(")", "Bracket"));
        delimiterList.add(new Token("[", "Bracket"));
        delimiterList.add(new Token("]", "Bracket"));

        keywordList.add(new Token("int", "Type"));
        keywordList.add(new Token("char", "Type"));
        keywordList.add(new Token("short", "Type"));
        keywordList.add(new Token("int", "Type"));
        keywordList.add(new Token("long", "Type"));
        keywordList.add(new Token("signed", "Type"));
        keywordList.add(new Token("unsigned", "Type"));
        keywordList.add(new Token("float", "Type"));
        keywordList.add(new Token("double", "Type"));
        keywordList.add(new Token("void", "Type"));
        keywordList.add(new Token("boolean", "Type"));

        keywordList.add(new Token("return", "KEYWORD"));
        keywordList.add(new Token("static", "KEYWORD"));
        keywordList.add(new Token("if", "KEYWORD"));
        keywordList.add(new Token("else", "KEYWORD"));
        keywordList.add(new Token("for", "KEYWORD"));
        keywordList.add(new Token("do", "KEYWORD"));
        keywordList.add(new Token("while", "KEYWORD"));

        //binary
        operatorList.add(new Token("+", "Operator"));
        operatorList.add(new Token("-", "Operator"));
        operatorList.add(new Token("*", "Operator"));
        operatorList.add(new Token("/", "Operator"));
        operatorList.add(new Token(">", "Operator"));
        operatorList.add(new Token("<", "Operator"));

        //binary && assingment
        operatorList.add(new Token("=", "Operator"));
        operatorList.add(new Token("==", "StateOperator"));
        operatorList.add(new Token("-=", "StateOperator"));
        operatorList.add(new Token("+=", "StateOperator"));
        operatorList.add(new Token("*=", "StateOperator"));
        operatorList.add(new Token("/=", "StateOperator"));

        //unary
        operatorList.add(new Token("++", "Operator"));
        operatorList.add(new Token("--", "Operator"));
    }

    @Override
    public void formTokenRules(List<Token> tokens) {
        Terminal terminal;
        for (Token token : tokens) {
            if (token.getType().equals("Identifier")) {
                getRules().add(new Rule(new NonTerminal("Identifier"), new Terminal(token)));
                terminal = new Terminal(token.getSign());
                getTerminals().add(terminal);
            }
            if (token.getType().equals("Operator")) {
                getRules().add(new Rule(new NonTerminal("Operator"), new Terminal(token)));
                terminal = new Terminal(token.getSign());
                getTerminals().add(terminal);
            }
            if (token.getType().equals("StateOperator")) {
                getRules().add(new Rule(new NonTerminal("StateOperator"), new Terminal(token)));
                terminal = new Terminal(token.getSign());
                getTerminals().add(terminal);
            }
            if (token.getType().equals("Constant")) {
                getRules().add(new Rule(new NonTerminal("Constant"), new Terminal(token)));
                terminal = new Terminal(token.getSign());
                getTerminals().add(terminal);
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
    }

    @Override
    public boolean checkIfAllowedString(String string) {
        return string.matches("[a-zA-Z0-9=+*/%;&|\\[\\]_(),\\\"'.<> {}-]+$");
    }
}
