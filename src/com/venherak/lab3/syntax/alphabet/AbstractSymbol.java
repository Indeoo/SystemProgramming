package com.venherak.lab3.syntax.alphabet;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSymbol {
    private AbstractSymbol parent;
    private List<AbstractSymbol> children;
    private String literal;

    public AbstractSymbol(String literal) {
        this.literal = literal;
        children = new ArrayList<>();
        parent = null;
    }

    protected AbstractSymbol() {
        children = new ArrayList<>();
    }

    public void addParent(AbstractSymbol abstractSymbol) {
        setParent(abstractSymbol);
        parent.children.add(this);
    }

    public String getLiteral() {
        return literal;
    }

    public void setParent(AbstractSymbol parent) {
        this.parent = parent;
    }

    public AbstractSymbol getRoot() {
        if(getParent() != null) {
           return getParent().getRoot();
        }
        return this;
    }

    public AbstractSymbol getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return literal;
    }

    @Override
    public int hashCode() {
        return literal.hashCode();
    }

    public List<AbstractSymbol> getChildren() {
        return children;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
