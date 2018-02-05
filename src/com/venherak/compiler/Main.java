package com.venherak.compiler;

import com.venherak.compiler.exceptions.SemanticException;
import com.venherak.compiler.exceptions.SyntaxException;
import com.venherak.compiler.languages.Language;
import com.venherak.compiler.languages.LanguageC;
import com.venherak.compiler.lexical.Lexer;
import com.venherak.compiler.exceptions.WrongTokenException;
import com.venherak.compiler.syntax.Parser;

public class Main {

    public static void main(String[] args) {
        String code = "{ float b; int n1; n+=b; }";
        System.out.println(code + "\n");
        try {
            Language language = new LanguageC();

            Lexer lexer = new Lexer(code, language);
            lexer.formTokens();
            System.out.println(lexer);

            language.formTokenRules(lexer.getLexemeTableList());
            language.viewRules();

            Parser parser = new Parser(lexer.getLexemeTableList(), language);
            parser.parse();
            parser.viewTree(parser.getTerminals().get(0).getRoot());
            parser.analyzeSemantics();

        } catch (WrongTokenException | SyntaxException | SemanticException e) {
            e.printStackTrace();
        }
    }
}