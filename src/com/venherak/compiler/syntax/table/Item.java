package com.venherak.compiler.syntax.table;

import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.SymbolChain;
import com.venherak.compiler.syntax.alphabet.Terminal;

public class Item {
    private Rule rule;
    private int point;
    private boolean finish;

    public Item(Rule rule) {
        this.rule = rule;
        this.point = 0;
        this.finish = false;
    }

    public Item(Rule rule, int point) {
        this.rule = rule;
        this.point = point;
        this.finish = false;
    }

    public SymbolChain getAlreadyRead() {
        SymbolChain symbols = new SymbolChain();
        for (int i = 0; i < point; i++) {
            symbols.add(rule.getRight().get(i));
        }
        return symbols;
    }

    public void shiftPoint() {
        if (point != rule.getRight().size()) {
            point++;
        } else {
            finish = true;
        }
    }

    public AbstractSymbol getRightSymbol() {
        if (point < rule.getRight().size()) {
            return rule.getRight().get(point);
        } else {
            return new Terminal("FINAL");
        }
    }

    public AbstractSymbol getRuleSymbol() {
        return rule.getLeft();
    }

    public Item getShiftedCopy() {
        Item item = new Item(getRule(), point + 1);
        if ((point + 1) == rule.getRight().size()) {
            item.finish = true;
            return item;
        } else {
            return item;
        }
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Rule getRule() {
        return rule;
    }

    public boolean isFinish() {
        return finish;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("").append(rule.getLeft()).append("->");
        for (int i = 0; i < this.rule.getRight().size(); i++) {
            if (point == i) {
                result.append(".");
            }
            result.append(this.rule.getRight().get(i));
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return point + rule.hashCode() / 2 + getRightSymbol().hashCode() / 2;
    }
}
