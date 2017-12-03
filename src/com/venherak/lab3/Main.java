package com.venherak.lab3;

import com.venherak.lab3.Exceptions.SyntaxException;
import com.venherak.lab3.languages.LanguagePascal;
import com.venherak.lab3.lexical.Tokenizer;
import com.venherak.lab3.Exceptions.WrongTokenException;
import com.venherak.lab3.syntax.Parser;

public class Main {

    public static void main(String[] args) {
        String code = "if (a<c) then begin for i:=1 to n do begin end; end;";
        System.out.println(code + "\n");

        //code = code.replaceAll(" ", "");
        LanguagePascal language = new LanguagePascal();

        Tokenizer tokenizer = new Tokenizer(code, language);
        language.viewRules();
        System.out.println();
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
