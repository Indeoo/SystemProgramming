package com.venherak.compiler;

import com.venherak.compiler.exceptions.SyntaxException;
import com.venherak.compiler.languages.Language;
import com.venherak.compiler.languages.LanguagePascal;
import com.venherak.compiler.languages.LanguageTest;
import com.venherak.compiler.lexical.Token;
import com.venherak.compiler.lexical.Tokenizer;
import com.venherak.compiler.exceptions.WrongTokenException;
import com.venherak.compiler.syntax.Parser;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String code = "if (a<b) then begin a:=b+c; end;";
        System.out.println(code + "\n");

        Language language = new LanguagePascal();

        Tokenizer tokenizer = new Tokenizer(code, language);

        try {
            tokenizer.formTokens();
            System.out.println(tokenizer);

            language.formTokenRules(tokenizer.getLexemeTableList());
            language.viewRules();

            SymbolChain lexems = new SymbolChain();
/*            lexems.add(new Terminal("a"));
            lexems.add(new Terminal("a"));
            lexems.add(new Terminal("b"));
            lexems.add(new Terminal("b"));*/

            Parser parser = new Parser(tokenizer.getLexemeTableList(), language);
           // Parser parser = new Parser(language);
            parser.getTerminals().addAll(lexems);

            parser.formTable();

            parser.viewTree(parser.getTerminals().get(0).getRoot());

        } catch (WrongTokenException | SyntaxException e) {
            e.printStackTrace();
        }
    }
}
