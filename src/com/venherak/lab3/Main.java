package com.venherak.lab3;

import com.venherak.lab3.exceptions.SyntaxException;
import com.venherak.lab3.languages.LanguagePascal;
import com.venherak.lab3.lexical.Tokenizer;
import com.venherak.lab3.exceptions.WrongTokenException;
import com.venherak.lab3.syntax.Parser;

public class Main {

    public static void main(String[] args) {
        String code = "c=a+b+";
        System.out.println(code + "\n");

        LanguagePascal language = new LanguagePascal();

        Tokenizer tokenizer = new Tokenizer(code, language);

        try {
            tokenizer.formTokens();

            System.out.println(tokenizer);

            language.formTokenRules(tokenizer.getLexemeTableList());

            language.viewRules();
            Parser parser = new Parser(tokenizer.getLexemeTableList(), language);
            parser.formTree();
            parser.viewTree(parser.getTerminals().get(0).getRoot());

        } catch (WrongTokenException | SyntaxException e) {
            e.printStackTrace();
        }
    }
}
