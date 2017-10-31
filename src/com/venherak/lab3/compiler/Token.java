package com.venherak.lab3.compiler;

public class Token {

    private String sign;
    private String type;

    public Token(String sign, String type) {
        this.sign = sign;
        this.type = type;
    }

    String getSign() {
        return sign;
    }

    String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        return (o.getClass().equals(this.getClass()) && o.hashCode() == this.hashCode());
    }

    @Override
    public int hashCode() {
        return sign.hashCode() + type.hashCode();
    }

    @Override
    public String toString() {
        if (sign.equals(" ")) {
            return type + ":    " + "' '";
        }
        return type + ":    " + sign;
    }
}