package com.venherak.compiler;

import com.venherak.compiler.exceptions.SyntaxException;
import com.venherak.compiler.languages.Language;
import com.venherak.compiler.languages.LanguageC;
import com.venherak.compiler.languages.LanguageTest;
import com.venherak.compiler.lexical.Tokenizer;
import com.venherak.compiler.exceptions.WrongTokenException;
import com.venherak.compiler.syntax.Parser;
import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.NonTerminal;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;
import com.venherak.compiler.syntax.table.Item;

public class Main {

    public static void main(String[] args) {
        String code = "aabb";
        System.out.println(code + "\n");

        //LanguagePascal language = new LanguagePascal();
        LanguageTest language = new LanguageTest();

        Tokenizer tokenizer = new Tokenizer(code, language);

        NonTerminal nonTerminal = new NonTerminal("A");
        Rule rule = new Rule(nonTerminal, new SymbolChain());
        rule.getRight().add(new Terminal("1"));
        rule.getRight().add(new Terminal("2"));
        rule.getRight().add(new Terminal("3"));
        rule.getRight().add(new Terminal("4"));
        Item item = new Item(rule);
        Language language1 = new LanguageTest();
        Parser parser = new Parser(language1);
        System.out.println(language1);
        parser.formTable();

/*        try {
            tokenizer.formTokens();

            System.out.println(tokenizer);

            language.formTokenRules(tokenizer.getLexemeTableList());

            language.viewRules();
            //Parser parser = new Parser(tokenizer.getLexemeTableList(), language);


            Parser parser = new Parser(language);
             parser.getTerminals().add(new Terminal("a"));
            parser.getTerminals().add(new Terminal("a"));
            parser.getTerminals().add(new Terminal("b"));
            parser.getTerminals().add(new Terminal("b"));

            parser.formTree();
            parser.viewTree(parser.getTerminals().get(0).getRoot());

        } catch (WrongTokenException | SyntaxException e) {
            e.printStackTrace();
        }*/
    }
}
