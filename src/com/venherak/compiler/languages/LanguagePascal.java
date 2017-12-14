package com.venherak.compiler.languages;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.NonTerminal;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LanguagePascal extends Language {

    public LanguagePascal() {
        super();
    }

    public void formRules() {
        List<Rule> ruleList = new ArrayList<>();

        NonTerminal ROOT = new NonTerminal("ROOT");
        NonTerminal STATEMENTS = new NonTerminal("Statements");
        NonTerminal FORSTATEMENT = new NonTerminal("ForStatement");
        NonTerminal IFSTATEMENT = new NonTerminal("IFStatement");
        NonTerminal STATEMENT = new NonTerminal("Statement");
        NonTerminal ARRAYEXPRESSION = new NonTerminal("ArrayExpression");
        NonTerminal EXPRESSION = new NonTerminal("Expression");
        NonTerminal DELIMITER = new NonTerminal("Delimiter");
        NonTerminal IDENTIFIER = new NonTerminal("Identifier");
        NonTerminal OPERATOR = new NonTerminal("Operator");
        NonTerminal CONSTANT = new NonTerminal("Constant");
        NonTerminal BRACKETS = new NonTerminal("Brackets");
        NonTerminal BEGINEND = new NonTerminal("BeginEnd");
        NonTerminal StateOperator = new NonTerminal("StateOperator");

        Terminal SPACETerminal = new Terminal(" ");

        getNonTerminals().add(STATEMENT);
        getNonTerminals().add(EXPRESSION);
        getNonTerminals().add(DELIMITER);
        getNonTerminals().add(IDENTIFIER);
        getNonTerminals().add(OPERATOR);
        getNonTerminals().add(CONSTANT);
        getNonTerminals().add(BRACKETS);

        Terminal squareDelimLeft = new Terminal("[");
        Terminal squareDelimRIght = new Terminal("]");
        getTerminals().add(squareDelimLeft);
        getTerminals().add(squareDelimRIght);

        Rule arrayRule = new Rule(ARRAYEXPRESSION, new SymbolChain());
        arrayRule.getRight().add(squareDelimLeft);
        arrayRule.getRight().add(EXPRESSION);
        arrayRule.getRight().add(squareDelimRIght);

        Rule ruleBrackets4 = new Rule(EXPRESSION, new SymbolChain());
        ruleBrackets4.getRight().add(new Terminal("("));
        ruleBrackets4.getRight().add((EXPRESSION));
        ruleBrackets4.getRight().add(new Terminal(")"));

        Rule ruleBrackets3 = new Rule(EXPRESSION, new SymbolChain());
        ruleBrackets3.getRight().add(EXPRESSION);
        ruleBrackets3.getRight().add(OPERATOR);
        ruleBrackets3.getRight().add(EXPRESSION);

        Rule eRule1 = new Rule(EXPRESSION, new SymbolChain());
        eRule1.getRight().add(IDENTIFIER);

        Rule eRule2 = new Rule(EXPRESSION, new SymbolChain());
        eRule2.getRight().add(EXPRESSION);
        eRule2.getRight().add(OPERATOR);
        eRule2.getRight().add(IDENTIFIER);

        Rule eRule3 = new Rule(EXPRESSION, new SymbolChain());
        eRule3.getRight().add(IDENTIFIER);
        eRule3.getRight().add(OPERATOR);
        eRule3.getRight().add(IDENTIFIER);

        Rule rule7 = new Rule(STATEMENT, new SymbolChain());
        rule7.getRight().add(IDENTIFIER);
        rule7.getRight().add(StateOperator);
        rule7.getRight().add(EXPRESSION);

        Rule rule8 = new Rule(EXPRESSION, new SymbolChain());
        rule8.getRight().add(CONSTANT);

        Rule rule9 = new Rule(IFSTATEMENT, new SymbolChain());
        rule9.getRight().add(new Terminal("if"));
        rule9.getRight().add(EXPRESSION);
        rule9.getRight().add(new Terminal("then"));

        Rule rule10 = new Rule(FORSTATEMENT, new SymbolChain());
        rule10.getRight().add(new Terminal("for"));
        rule10.getRight().add(SPACETerminal);
        rule10.getRight().add(STATEMENT);
        rule10.getRight().add(SPACETerminal);
        rule10.getRight().add(new Terminal("to"));
        rule10.getRight().add(SPACETerminal);
        rule10.getRight().add(EXPRESSION);
        rule10.getRight().add(SPACETerminal);
        rule10.getRight().add(new Terminal("do"));

        Rule forStatement1 = new Rule(STATEMENT, new SymbolChain());
        forStatement1.getRight().add(FORSTATEMENT);
        forStatement1.getRight().add(SPACETerminal);
        forStatement1.getRight().add(new Terminal("begin"));
        forStatement1.getRight().add(SPACETerminal);
        forStatement1.getRight().add(new Terminal("end"));

        Rule forStatement2 = new Rule(STATEMENT, new SymbolChain());
        forStatement2.getRight().add(FORSTATEMENT);
        forStatement2.getRight().add(SPACETerminal);
        forStatement2.getRight().add(new Terminal("begin"));
        forStatement2.getRight().add(STATEMENTS);
        forStatement2.getRight().add(new Terminal("end"));

        Rule forStatement3 = new Rule(STATEMENT, new SymbolChain());
        forStatement3.getRight().add(FORSTATEMENT);
        forStatement3.getRight().add(STATEMENTS);

        Rule ifStatement2 = new Rule(STATEMENT, new SymbolChain());
        ifStatement2.getRight().add(IFSTATEMENT);
        ifStatement2.getRight().add(STATEMENT);

        Rule ifStatement3 = new Rule(STATEMENT, new SymbolChain());
        ifStatement3.getRight().add(IFSTATEMENT);
        ifStatement3.getRight().add(BEGINEND);

        Rule rule12 = new Rule(STATEMENT, new SymbolChain());
        rule12.getRight().add(STATEMENT);

        Rule rule13 = new Rule(STATEMENT, new SymbolChain());
        rule13.getRight().add(IDENTIFIER);
        rule13.getRight().add(StateOperator);
        rule13.getRight().add(EXPRESSION);
        rule13.getRight().add(new Terminal(";"));

        Rule rule14 = new Rule(STATEMENTS, new SymbolChain());
        rule14.getRight().add(STATEMENT);

        Rule ruleRoot = new Rule(ROOT, new SymbolChain());
        ruleRoot.getRight().add(STATEMENTS);

        Rule rule15  = new Rule(STATEMENT, new SymbolChain());
        rule15.getRight().add(IDENTIFIER);
        rule15.getRight().add(StateOperator);
        rule15.getRight().add(IDENTIFIER);

        Rule beginEndRule1 = new Rule(BEGINEND, new SymbolChain());
        beginEndRule1.getRight().add(new Terminal("begin"));
        beginEndRule1.getRight().add(STATEMENTS);
        beginEndRule1.getRight().add(new Terminal("end"));
        beginEndRule1.getRight().add(new Terminal(";"));

        Rule beginEndRule2 = new Rule(BEGINEND, new SymbolChain());
        beginEndRule2.getRight().add(new Terminal("begin"));
        beginEndRule2.getRight().add(new Terminal("end"));
        beginEndRule2.getRight().add(new Terminal(";"));

        //ruleList.add(ruleBrackets3);
        ruleList.add(eRule1);
        ruleList.add(eRule2);
        ruleList.add(eRule3);
       // ruleList.add(rule7);
        ruleList.add(rule8);
        ruleList.add(ruleBrackets4);
        ruleList.add(rule9);
        ruleList.add(rule10);
       // ruleList.add(forStatement1);
        //ruleList.add(forStatement2);
       // ruleList.add(forStatement3);
        ruleList.add(ifStatement2);
        ruleList.add(ifStatement3);
        ruleList.add(rule13);
        ruleList.add(rule14);
        ruleList.add(ruleRoot);
        ruleList.add(beginEndRule1);
        ruleList.add(beginEndRule2);

        getRules().addAll(ruleList);

    }

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
        keywordList.add(new Token("byte", "Type"));
        keywordList.add(new Token("int", "Type"));
        keywordList.add(new Token("real", "Type"));
        keywordList.add(new Token("double", "Type"));
        keywordList.add(new Token("boolean", "Type"));

        keywordList.add(new Token("return", "Keyword"));
        keywordList.add(new Token("if", "Keyword"));
        keywordList.add(new Token("else", "Keyword"));
        keywordList.add(new Token("for", "Keyword"));
        keywordList.add(new Token("do", "Keyword"));
        keywordList.add(new Token("then", "Keyword"));
        keywordList.add(new Token("while", "Keyword"));
        keywordList.add(new Token("begin", "Keyword"));
        keywordList.add(new Token("end", "Keyword"));
        keywordList.add(new Token("to", "Keyword"));

        //binary
        operatorList.add(new Token("+", "Operator"));
        operatorList.add(new Token("-", "Operator"));
        operatorList.add(new Token("*", "Operator"));
        operatorList.add(new Token("/", "Operator"));
        operatorList.add(new Token(">", "Operator"));
        operatorList.add(new Token("<", "Operator"));
        operatorList.add(new Token(":", "Operator"));

        //binary && assingment
        operatorList.add(new Token("=", "Operator"));
        operatorList.add(new Token("-=", "StateOperator"));
        operatorList.add(new Token("+=", "StateOperator"));
        operatorList.add(new Token("*=", "StateOperator"));
        operatorList.add(new Token("/=", "StateOperator"));
        operatorList.add(new Token(":=", "StateOperator"));
    }

    public boolean checkIfAllowedString(String string) {
        return string.matches("[a-zA-Z0-9=+*/%;&:|\\[\\]_(),\\\"'.<> {}-]+$");
    }
}