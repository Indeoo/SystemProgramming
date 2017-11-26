package com.venherak.lab3;

import com.venherak.lab3.Exceptions.SyntaxException;
import com.venherak.lab3.lexical.Tokenizer;
import com.venherak.lab3.Exceptions.WrongTokenException;
import com.venherak.lab3.syntax.Parser;

public class Main {

    public static void main(String[] args) {

        String code = "a+=b;";
        System.out.println(code + "\n");

        LanguageC language = new LanguageC();

        Tokenizer tokenizer = new Tokenizer(code, language);
        try {
            tokenizer.formTokens();

            System.out.println(tokenizer);

            language.formTokenRules(tokenizer.getLexemeTableList());

            language.viewRules();
            Parser parser = new Parser(tokenizer.getLexemeTableList(), language);
            parser.formTree();
            parser.viewTree(language.getRoot());

        } catch (WrongTokenException | SyntaxException e) {
            e.printStackTrace();
        }
    }
}
