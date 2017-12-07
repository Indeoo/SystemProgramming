package com.venherak.compiler.syntax.table;

import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.SymbolChain;

public class Item {
    private Rule rule;
    private int point;

    public Item(Rule rule) {
        this.rule = rule;
        this.point = 0;
    }

    public Item(Rule rule, int point) {
        this.rule = rule;
        this.point = point;
    }

    public SymbolChain getAlreadyRead() {
        SymbolChain symbols = new SymbolChain();
        for (int i = 0; i < point; i++) {
            symbols.add(rule.getRight().get(i));
        }
        return symbols;
    }

    public void shiftPoint() {
        if(point != rule.getRight().size()) {
            point++;
        }
    }

    public AbstractSymbol getRightSymbol() {
            return rule.getRight().get(point);
    }
}
