package com.venherak.compiler.syntax;

public class State {
    Rule rule;
    int pointer;

    public State(Rule rule, int pointer) {
        this.rule = rule;
        this.pointer = pointer;
    }
}
