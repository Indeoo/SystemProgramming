package com.venherak.lab2;

import com.venherak.lab2.automate.Automate;
import com.venherak.lab2.tree.ParseTree;

public class Main {
    static String[] signals = {"dlm", "ltr", "ltr", "dlm", "ltr", "ltr", "ltr"};

    public static void main(String args[]) {

        Automate automate = new Automate();

        System.out.print("Signals: 1");
        for(String signal: signals) {
            System.out.print(signal + " ");
        }

        System.out.println("\n\n" + automate.toString());

        try {
            automate.performSignalArray(signals);
            automate.performSignal("ltr");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(automate.toString());

        ParseTree parseTree = new ParseTree();
        parseTree.print();
    }
}
