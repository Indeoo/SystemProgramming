package com.venherak.compiler.languages;

import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.NonTerminal;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LanguageC extends Language {

    public LanguageC() {
        super();
    }

    public void formRules() {
        NonTerminal ROOT = new NonTerminal("ROOT");
        NonTerminal Function = new NonTerminal("Function");
        NonTerminal StatementChain = new NonTerminal("StatementChain");
        NonTerminal FULLSTATEMENT = new NonTerminal("Statements");
        NonTerminal STATEMENT = new NonTerminal("Statement");
        NonTerminal ARRAYEXPRESSION = new NonTerminal("ArrayExpression");
        NonTerminal CODEBLOCK = new NonTerminal("CodeBlock");
        NonTerminal EXPRESSION = new NonTerminal("Expression");
        NonTerminal DELIMITER = new NonTerminal("Delimiter");
        NonTerminal IDENTIFIER = new NonTerminal("Identifier");
        NonTerminal OPERATOR = new NonTerminal("Operator");
        NonTerminal CONSTANT = new NonTerminal("Constant");
        NonTerminal IDENTIFIERLIST = new NonTerminal("IdentifierList");
        NonTerminal TYPE = new NonTerminal("Type");
        NonTerminal DECLARATION = new NonTerminal("Declaration");
        NonTerminal BRACKETS = new NonTerminal("Brackets");
        NonTerminal StateOperator = new NonTerminal("StateOperator");
        getNonTerminals().add(STATEMENT);
        getNonTerminals().add(EXPRESSION);
        getNonTerminals().add(DELIMITER);
        getNonTerminals().add(IDENTIFIER);
        getNonTerminals().add(OPERATOR);
        getNonTerminals().add(CONSTANT);
        getNonTerminals().add(BRACKETS);

        Rule ruleRoot = new Rule(ROOT, new SymbolChain());
        ruleRoot.getRight().add(CODEBLOCK);

        Terminal squareDelimLeft = new Terminal("[");
        Terminal squareDelimRIght = new Terminal("]");
        getTerminals().add(squareDelimLeft);
        getTerminals().add(squareDelimRIght);

        List<Rule> ruleList = new ArrayList<>();

        Rule eRule1 = new Rule(EXPRESSION, new SymbolChain());
        eRule1.getRight().add(IDENTIFIER);

        Rule eRule2 = new Rule(EXPRESSION, new SymbolChain());
        eRule2.getRight().add(EXPRESSION);
        eRule2.getRight().add(OPERATOR);
        eRule2.getRight().add(IDENTIFIER);

        Rule eRule3 = new Rule(EXPRESSION, new SymbolChain());
        eRule3.getRight().add(ARRAYEXPRESSION);

        Rule eRule4 = new Rule(EXPRESSION, new SymbolChain());
        eRule4.getRight().add(IDENTIFIER);
        eRule4.getRight().add(OPERATOR);
        eRule4.getRight().add(IDENTIFIER);

        Rule arrayRule1 = new Rule(ARRAYEXPRESSION, new SymbolChain());
        arrayRule1.getRight().add(IDENTIFIER);
        arrayRule1.getRight().add(squareDelimLeft);
        arrayRule1.getRight().add(EXPRESSION);
        arrayRule1.getRight().add(squareDelimRIght);

        Rule arrayRule2 = new Rule(ARRAYEXPRESSION, new SymbolChain());
        arrayRule2.getRight().add(IDENTIFIER);
        arrayRule2.getRight().add(squareDelimLeft);
        arrayRule2.getRight().add(IDENTIFIER);
        arrayRule2.getRight().add(squareDelimRIght);

        Rule rule4 = new Rule(EXPRESSION, new SymbolChain());
        rule4.getRight().add(CONSTANT);
        rule4.getRight().add(OPERATOR);
        rule4.getRight().add(EXPRESSION);

        Rule rule5 = new Rule(EXPRESSION, new SymbolChain());
        rule5.getRight().add(CONSTANT);

        //Rule rule6 = new Rule(EXPRESSION, new SymbolChain());
        // rule6.getRight().add(EXPRESSION);
        // rule6.getRight().add(DELIMITER);

        Rule rule7 = new Rule(STATEMENT, new SymbolChain());
        rule7.getRight().add(IDENTIFIER);
        rule7.getRight().add(StateOperator);
        rule7.getRight().add(EXPRESSION);
        rule7.getRight().add(new Terminal(";"));
        //rule7.getRight().add(new Terminal(";"));

        Rule rule8 = new Rule(FULLSTATEMENT, new SymbolChain());
        rule8.getRight().add(FULLSTATEMENT);
        rule8.getRight().add(FULLSTATEMENT);

        Rule rule9 = new Rule(FULLSTATEMENT, new SymbolChain());
        rule9.getRight().add(STATEMENT);
        //rule9.getRight().add(new Terminal(";"));


        Rule ruleBrackets = new Rule(EXPRESSION, new SymbolChain());
        ruleBrackets.getRight().add(CONSTANT);
        ruleBrackets.getRight().add(OPERATOR);
        ruleBrackets.getRight().add(new Terminal("("));
        ruleBrackets.getRight().add(EXPRESSION);
        ruleBrackets.getRight().add(new Terminal(")"));

        Rule ruleBrackets2 = new Rule(EXPRESSION, new SymbolChain());
        ruleBrackets2.getRight().add(new Terminal("("));
        ruleBrackets2.getRight().add(EXPRESSION);
        ruleBrackets2.getRight().add(new Terminal(")"));
        ruleBrackets2.getRight().add(OPERATOR);
        ruleBrackets2.getRight().add(IDENTIFIER);

        Rule ruleBrackets3 = new Rule(EXPRESSION, new SymbolChain());
        ruleBrackets3.getRight().add(EXPRESSION);
        ruleBrackets3.getRight().add(OPERATOR);
        ruleBrackets3.getRight().add(EXPRESSION);

        Rule ruleBrackets4 = new Rule(EXPRESSION, new SymbolChain());
        ruleBrackets4.getRight().add(new Terminal("("));
        ruleBrackets4.getRight().add((EXPRESSION));
        ruleBrackets4.getRight().add(new Terminal(")"));

        Rule declaration1 = new Rule(DECLARATION, new SymbolChain());
        declaration1.getRight().add(TYPE);
        declaration1.getRight().add(IDENTIFIER);

        Rule declaration2 = new Rule(FULLSTATEMENT, new SymbolChain());
        declaration2.getRight().add(DECLARATION);
        declaration2.getRight().add(StateOperator);
        declaration2.getRight().add(EXPRESSION);
        declaration2.getRight().add(new Terminal(";"));

        Rule declaration3 = new Rule(FULLSTATEMENT, new SymbolChain());
        declaration3.getRight().add(DECLARATION);
        declaration3.getRight().add(StateOperator);
        declaration3.getRight().add(CONSTANT);
        declaration3.getRight().add(new Terminal(";"));

        Rule declaration4 = new Rule(FULLSTATEMENT, new SymbolChain());
        declaration4.getRight().add(DECLARATION);
        declaration4.getRight().add(StateOperator);
        declaration4.getRight().add(IDENTIFIER);
        declaration4.getRight().add(new Terminal(";"));

/*        Rule declaration3 = new Rule(DECLARATION, new SymbolChain());
        declaration3.getRight().add(TYPE);
        declaration3.getRight().add(IDENTIFIERLIST);*/

/*        Rule declist1 = new Rule(IDENTIFIERLIST, new SymbolChain());
        declist1.getRight().add(IDENTIFIER);
        declist1.getRight().add(new Terminal(","));
        declist1.getRight().add(IDENTIFIER);

        Rule declist2 = new Rule(IDENTIFIERLIST, new SymbolChain());
        declist2.getRight().add(IDENTIFIER);
        declist2.getRight().add(new Terminal(","));
        declist2.getRight().add(IDENTIFIERLIST);*/
/*        Rule declaration3 = new Rule(STATEMENT, new SymbolChain());
        declaration3.getRight().add(DECLARATION);
        declaration3.getRight().add(StateOperator);
        declaration3.getRight().add(IDENTIFIER);*/

        Rule declaration6 = new Rule(FULLSTATEMENT, new SymbolChain());
        declaration6.getRight().add(DECLARATION);
        declaration6.getRight().add(new Terminal(";"));

        Rule declaration5 = new Rule(DECLARATION, new SymbolChain());
        declaration5.getRight().add(DECLARATION);
        declaration5.getRight().add(new Terminal(","));
        declaration5.getRight().add(IDENTIFIER);

        Rule stchain1 = new Rule(StatementChain, new SymbolChain());
        stchain1.getRight().add(FULLSTATEMENT);
        stchain1.getRight().add(FULLSTATEMENT);

        Rule stchain2 = new Rule(StatementChain, new SymbolChain());
        stchain2.getRight().add(FULLSTATEMENT);

        Rule function1 = new Rule(Function, new SymbolChain());
        function1.getRight().add(DECLARATION);
        function1.getRight().add(new Terminal("("));
        function1.getRight().add(new Terminal(")"));
        function1.getRight().add(CODEBLOCK);

        Rule function2 = new Rule(Function, new SymbolChain());
        function2.getRight().add(DECLARATION);
        function2.getRight().add(new Terminal("("));
        function2.getRight().add(IDENTIFIER);
        function2.getRight().add(new Terminal(")"));
        function2.getRight().add(CODEBLOCK);

        Rule codeblock1 = new Rule(CODEBLOCK, new SymbolChain());
        codeblock1.getRight().add(new Terminal("{"));
        codeblock1.getRight().add(FULLSTATEMENT);
        codeblock1.getRight().add(new Terminal("}"));

        Rule codeblock2 = new Rule(CODEBLOCK, new SymbolChain());
        codeblock2.getRight().add(new Terminal("{"));
        codeblock2.getRight().add(new Terminal("}"));

        ruleList.add(ruleRoot);
        //ruleList.add(eRule1);
        ruleList.add(eRule2);
        ruleList.add(eRule3);
        ruleList.add(eRule4);
        ruleList.add(ruleBrackets);
        ruleList.add(ruleBrackets2);
        ruleList.add(ruleBrackets3);
        ruleList.add(rule4);
        ruleList.add(rule5);
        ruleList.add(rule7);
        ruleList.add(rule8);
        ruleList.add(rule9);
        ruleList.add(arrayRule1);
        ruleList.add(arrayRule2);
        ruleList.add(ruleBrackets4);
        ruleList.add(declaration1);
        ruleList.add(declaration2);
        ruleList.add(declaration3);
        ruleList.add(declaration4);
        ruleList.add(declaration5);
        ruleList.add(declaration6);
        ruleList.add(stchain1);
        //ruleList.add(stchain2);
        ruleList.add(function1);
        ruleList.add(function2);
        ruleList.add(codeblock1);
        ruleList.add(codeblock2);
        //ruleList.add(declist1);
        //ruleList.add(declist2);

        //ruleList.add(rule6);
        getRules().addAll(ruleList);

    }

    public void formTokenRules(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.getType().equals("Identifier")) {
                getRules().add(new Rule(new NonTerminal("Identifier"), new Terminal(token)));
                getTerminals().add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("Operator")) {
                getRules().add(new Rule(new NonTerminal("Operator"), new Terminal(token)));
                getTerminals().add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("StateOperator")) {
                getRules().add(new Rule(new NonTerminal("StateOperator"), new Terminal(token)));
                getTerminals().add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("Constant")) {
                getRules().add(new Rule(new NonTerminal("Constant"), new Terminal(token)));
                getTerminals().add(new Terminal(token.getSign()));
            }
            if (token.getType().equals("Type")) {
                getRules().add(new Rule(new NonTerminal("Type"), new Terminal(token)));
                getTerminals().add(new Terminal(token.getSign()));
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
        keywordList.add(new Token("short", "Type"));
        keywordList.add(new Token("long", "Type"));
        keywordList.add(new Token("signed", "Type"));
        keywordList.add(new Token("unsigned", "Type"));
        keywordList.add(new Token("float", "Type"));
        keywordList.add(new Token("double", "Type"));
        keywordList.add(new Token("void", "Type"));

        keywordList.add(new Token("return", "KEYWORD"));
        keywordList.add(new Token("static", "KEYWORD"));
        keywordList.add(new Token("if", "KEYWORD"));
        keywordList.add(new Token("else", "KEYWORD"));
        keywordList.add(new Token("for", "KEYWORD"));
        keywordList.add(new Token("while", "KEYWORD"));
/*        keywordList.add(new Token("false", "Type"));
        keywordList.add(new Token("true", "Type"));*/

        //binary
        operatorList.add(new Token("+", "Operator"));
        operatorList.add(new Token("-", "Operator"));
        operatorList.add(new Token("*", "Operator"));
        operatorList.add(new Token("/", "Operator"));
        operatorList.add(new Token(">", "Operator"));
        operatorList.add(new Token("<", "Operator"));
        operatorList.add(new Token("==", "Operator"));

        //binary && assingment
        operatorList.add(new Token("=", "Operator"));
        operatorList.add(new Token("-=", "StateOperator"));
        operatorList.add(new Token("+=", "StateOperator"));
        operatorList.add(new Token("*=", "StateOperator"));
        operatorList.add(new Token("/=", "StateOperator"));

        //unary
        operatorList.add(new Token("++", "Operator"));
        operatorList.add(new Token("--", "Operator"));
    }

    public boolean checkIfAllowedString(String string) {
        return string.matches("[a-zA-Z0-9=+*/%;&|\\[\\]_(),\"'.<> {}-]+$");
    }
}
