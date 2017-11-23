package com.venherak.lab4;

/**
 * Created by User on 22.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        Tree tree = analyzer.parse("If ( a > b ) then begin end else begin end ;");
        tree.print();
        int a = 2;
        int b = 3;
        int c = 0;
        int d =2;
        c += a += b += d;
        System.out.println(c);
        TreePrinter printer = new TreePrinter();
        printer.print(tree.getRoot());
    }
}
