package com.venherak.lab2.tree;

public class Lexeme {
    String value;
    Lexeme left;
    Lexeme right;
    boolean isTerminal;
    Lexeme parent;

    public Lexeme(String value, Lexeme left, Lexeme right, boolean isTerminal) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.isTerminal = isTerminal;
    }
}
