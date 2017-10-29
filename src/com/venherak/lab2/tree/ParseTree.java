package com.venherak.lab2.tree;

import java.util.ArrayList;

public class ParseTree {

    ArrayList<Lexeme> nodes;
    StringBuilder result;

    public ParseTree() {
        result = new StringBuilder();
        nodes = new ArrayList<>();

        Lexeme node00 = new Lexeme("n", null, null, false);
        Lexeme node01 = new Lexeme("0", null, null, false);
        Lexeme node02 = new Lexeme("n", null, null, false);
        Lexeme node03 = new Lexeme("n", null, null, false);
        Lexeme node05 = new Lexeme(" b", null, null, false);
        Lexeme node06 = new Lexeme("b", null, null, false);
        Lexeme node07 = new Lexeme("a", null, null, false);
        Lexeme node08 = new Lexeme("n", null, null, false);
        Lexeme node09 = new Lexeme("<>", node00, node01, true);
        Lexeme node11 = new Lexeme("[n]", node07, null, true);
        Lexeme node12 = new Lexeme("+", node06, node11, true);
        Lexeme node13 = new Lexeme(":=", node05, node12, true);
        Lexeme node04 = new Lexeme("1;", null, node13, false);
        Lexeme node10 = new Lexeme("-", node03, node04, true);
        Lexeme node14 = new Lexeme(":=", node02, node10, true);
        Lexeme node16 = new Lexeme(" end", node14, null, false);
        Lexeme node15 = new Lexeme(" while ", null, node09, true);
        Lexeme node17 = new Lexeme(" begin ", null, node16, true);
        Lexeme node18 = new Lexeme(" do", node15, node17, true);




        node00.parent = node09;
        node01.parent = node09;
        node02.parent = node14;
        node03.parent = node10;
        node04.parent = node10;
        node05.parent = node13;
        node06.parent = node12;
        node07.parent = node11;
        node08.parent = node11;
        node09.parent = node18;
        node10.parent = node14;
        node11.parent = node12;
        node12.parent = node13;
        node13.parent = node15;
        node14.parent = node15;
        node15.parent = node17;
        node16.parent = node17;
        node17.parent = node18;


        nodes.add(node00);
        nodes.add(node01);
        nodes.add(node02);
        nodes.add(node03);
        nodes.add(node04);
        nodes.add(node05);
        nodes.add(node06);
        nodes.add(node07);
        nodes.add(node08);
        nodes.add(node09);
        nodes.add(node10);
        nodes.add(node11);
        nodes.add(node12);
        nodes.add(node13);
        nodes.add(node14);
        nodes.add(node15);
        nodes.add(node16);
        nodes.add(node17);
        nodes.add(node18);

    }

    public void print() {
        Lexeme root = root(nodes.get(0));
        search(root);
        System.out.println(result);
    }

    private void search(Lexeme node) {

        if (node.left != null) {
            search(node.left);
        }
        result.append(node.value);
        if (node.right != null) {
            search(node.right);
        }
    }

    private Lexeme root(Lexeme node) {
        Lexeme result = node;
        if (node.parent != null) {
            result = root(node.parent);
        }
        return result;
    }
}
