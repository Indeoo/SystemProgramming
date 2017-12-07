package com.venherak.compiler.syntax.table;

import com.venherak.compiler.syntax.alphabet.AbstractSymbol;

import java.util.List;

public class State {
    private List<Item> itemList;
    AbstractSymbol signal;

    public State() {

    }

    void addItem(Item item) {
        itemList.add(item);
    }
}
