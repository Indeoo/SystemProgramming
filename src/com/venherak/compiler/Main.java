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
import com.venherak.compiler.syntax.table.State;

public class Main {

    public static void main(String[] args) {
        String code = "a=b+c;";
        System.out.println(code + "\n");

        LanguageC language = new LanguageC();

        Tokenizer tokenizer = new Tokenizer(code, language);

        try {
            tokenizer.formTokens();
            System.out.println(tokenizer);

            language.formTokenRules(tokenizer.getLexemeTableList());
            language.viewRules();
            Parser parser = new Parser(tokenizer.getLexemeTableList(), language);

            parser.formTable();

            parser.viewTree(parser.getTerminals().get(0).getRoot());

        } catch (WrongTokenException | SyntaxException e) {
            e.printStackTrace();
        }
    }
}
