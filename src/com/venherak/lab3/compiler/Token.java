package com.venherak.lab3.compiler;

public class Token {

    private String sign;
    private String title;

    public Token(String sign, String title) {
        this.sign = sign;
        this.title = title;
    }

    public Token() {

    }

    public String getSign() {
        return sign;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return sign.hashCode() + title.hashCode();
    }

    @Override
    public String toString() {
        if (sign.equals(" ")) {
            return title + ":    " + "' '";
        }
        return title + ":    " + sign;
    }
}
